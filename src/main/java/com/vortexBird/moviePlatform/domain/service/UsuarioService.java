package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.*;
import com.vortexBird.moviePlatform.domain.customExceptions.*;
import com.vortexBird.moviePlatform.domain.dto.*;
import com.vortexBird.moviePlatform.domain.repository.*;
import com.vortexBird.moviePlatform.persistence.enums.TokenType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.NoResultException;
import jakarta.persistence.PessimisticLockException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.vortexBird.moviePlatform.web.utils.SecurityUtils.validateUser;

@Service
public class UsuarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DetallesUsuarioRepository detallesUsuarioRepository;

    @Autowired
    private MetodoPagoUsuarioService metodoPagoUsuarioService;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private TiqueteService tiqueteService;

    @Autowired
    private TiqueteRepository tiqueteRepository;

    @Autowired
    private SillaRepository sillaRepository;

    @Autowired
    private SalaCineRepository salaCineRepository;

    @Autowired
    private FuncionService funcionService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenUsuarioService tokenUsuarioService;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.servlet.context-path}")
    private String basePath;

    public List<Usuario> obtenerTodos() {
        List<Usuario> emptyList = new ArrayList<>();
        return usuarioRepository.obtenerTodos().orElse(emptyList);
    }

    public Optional<Usuario> obtenerUsuario(String alias) {
        return usuarioRepository.obtenerPorAlias(alias);
    }

    public Optional<List<Usuario>> obtenerPorCualquierCoincidencia(String keyword){
        return usuarioRepository.obtenerPorCualquierCoincidencia(keyword);
    }

    @Retryable(
            value = {IncompleteTicketFetchException.class, PessimisticLockException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Transactional
    @Async
    public void comprarEntradas(TentativaCompraDTO tentativaCompraDTO){
        LOGGER.info("Comprando Entradas");
        long funcionId = tentativaCompraDTO.getIdFuncion();
        String alias = tentativaCompraDTO.getNickname();
        long metodoPagoUsuarioId = tentativaCompraDTO.getMetodoPagoUsuarioId();
        boolean success = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String codigoOrden = sdf.format(now) + "_" + alias + "#" + System.currentTimeMillis();
        LOGGER.info("Código de Orden generado: " + codigoOrden);
        Usuario usuario;
        usuario = obtenerUsuario(alias)
                .orElseThrow(() -> new InconsistencyException("Inconsistencia, el usuario debería existir, revisar"));
        MetodoPagoUsuario metodoPago = metodoPagoUsuarioService.obtenerMetodoPagoUsuario(metodoPagoUsuarioId)
                .orElseThrow(() -> new PaymentMethodNotFoundException("Inconsistency, payment method not found"));
        Funcion funcion = funcionService.obtenerfuncion(funcionId)
                .orElseThrow(() -> new InconsistencyException("Inconsistencia, la función debería existir, revisar"));
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setCodigoOrden(codigoOrden);
        compraDTO.setFechaCompra(new Timestamp(new Date().getTime()));
        compraDTO.setNombrePelicula(funcion.getPelicula().getTitulo());
        compraDTO.setNumeroSalaCine(funcion.getSalaCine().getNumeroSala());
        compraDTO.setFechaFuncion(funcion.getFecha());
        List<String> sillasTentativas = tentativaCompraDTO.getPosiblesSillas();
        List<Tiquete> posiblesTiquetesDisponibles = new ArrayList<>();
        double precioTotal = 0;
        try{
            posiblesTiquetesDisponibles = tiqueteRepository.obtenerPorCodigoSillasYFuncionConBloqueo(sillasTentativas, funcionId).orElseThrow(EntityNotFoundException::new);
            if (posiblesTiquetesDisponibles.size() != sillasTentativas.size()) {
                throw new IncompleteTicketFetchException("No todos los tiquetes estan disponibles ahora mismo.");
            }
            for (Tiquete tiquete : posiblesTiquetesDisponibles) {
                if (!tiquete.getDisponible()) {
                    throw new TicketAlreadySoldException("Tiquete " + tiquete.getTiqueteId() + " vendido.");
                }
            }
            precioTotal = posiblesTiquetesDisponibles.stream().mapToDouble(Tiquete::getPrecioIndividual).sum();
            validatePaymentMethod(metodoPago, precioTotal);
            compraDTO.setSillas(sillasTentativas);
        }catch (EntityNotFoundException e){
            LOGGER.info("Tiquetes no encontrados");
            LOGGER.error(e.getMessage(), e);
            success = false;
        } catch (IncompleteTicketFetchException | PessimisticLockException e) {
            LOGGER.info("Número de tiquetes a buscar incompletos o tiempo de espera para adquirir el lock vencido");
            LOGGER.error(e.getMessage(), e);
            success = false;
        } catch (TicketAlreadySoldException e) {
            LOGGER.info("Uno o mas tiquetes ya estan vendidos.");
            LOGGER.error(e.getMessage(), e);
            success = false;
        }
        if(success){
            //GENERAR FACTURA Y SUS DETALLES(SAVE FACTURA AND SAVE DETALLESFACTURA)
            Factura factura = new Factura();
            MetodoPagoUsuario metodoPagoUsuario = new MetodoPagoUsuario();
            metodoPagoUsuario.setMetodoPagoUsuarioId(metodoPagoUsuarioId);
            factura.setMetodoPagoUsuario(metodoPagoUsuario);
            factura.setFecha(new Timestamp(new Date().getTime()));
            factura.setPrecio(precioTotal);
            factura.setNumeroTiquetes(sillasTentativas.size());
            factura = facturaRepository.save(factura);
            List<DetalleFactura> detallesFactura = new ArrayList<>();
            DetalleFactura detalleFactura;
            //CAMBIAR ESTADO TIQUETES A NO DISPONIBLES(SERVICIO TIQUETE)
            for(Tiquete tiquete : posiblesTiquetesDisponibles){
                tiqueteService.inhabilitar(tiquete.getTiqueteId());
                detalleFactura = new DetalleFactura();
                detalleFactura.setFactura(factura);
                detalleFactura.setTiquete(tiquete);
                detallesFactura.add(detalleFactura);
            }
            detalleFacturaRepository.saveAll(detallesFactura);
            LOGGER.info("Compra confirmada");
            //Confirma Compra
            notifyPurchase(usuario, compraDTO, true);
        } else {
            notifyPurchase(usuario, compraDTO, false);
        }
    }

    public Optional<List<Factura>> obtenerComprasRealizadasPorUsuario(String alias){
        validateUser(alias);
        List<MetodoPagoUsuario> metodosPago;

        metodosPago = metodoPagoUsuarioService.obtenerMetodosPagoPorUsuario(alias)
                .orElseThrow(() -> new PaymentMethodNotFoundException("Not payment methods found it."));


        List<Factura> facturas = new ArrayList<>();
        for(MetodoPagoUsuario metodoPago : metodosPago){
            facturas.addAll(facturaRepository.obtenerPorMetodoPagoUsuario(metodoPago.getMetodoPagoUsuarioId()).orElse(new ArrayList<>()));
        }
        for(Factura factura : facturas){
            try{
                factura.setDetallesFactura(detalleFacturaRepository.obtenerPorFactura(factura.getFacturaId()).orElseThrow(NoResultException::new));
            }catch (NoResultException e){
                LOGGER.error(e.getMessage(), e);
            }
        }
        return Optional.of(facturas);
    }

    @Async
    public void registerUser(Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        try {
            usuario.setHabilitado(false);
            usuario.setContrasena(encoder.encode(usuario.getContrasena()));
            usuarioRepository.save(usuario);
            String fullBasePath = "http://localhost:" + serverPort + basePath;
            String subject = "Please Confirm Your Registration";
            String confirmationLink = fullBasePath + "/public/token/confirm?token=" + tokenUsuarioService.createTokenForUser(usuario, TokenType.CONFIRMATION);
            String message = "Welcome " + usuario.getAlias() + ",\n\n" +
                    "Thank you for registering. Please confirm your registration by clicking the following link:\n" +
                    confirmationLink;

            emailService.sendSimpleEmail(usuario.getCorreo(), subject, message);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Async
    public void sendPasswordRecoveryEmail(UsuarioRecoveryDTO usuarioRecoveryDTO) {
        Usuario usuario;
        try {
            if(!usuarioRecoveryDTO.getAlias().isEmpty()){
                usuario = usuarioRepository.obtenerPorAlias(usuarioRecoveryDTO.getAlias()).orElseThrow(NoResultException::new);
            } else {
                usuario = usuarioRepository.obtenerPorCorreo(usuarioRecoveryDTO.getCorreo()).orElseThrow(NoResultException::new);
            }
        } catch(NoResultException e){
            LOGGER.error("Usuario no encontrado por nickname o correo");
            LOGGER.error(e.getMessage(), e);
            return;
        }
        try {
            String fullBasePath = "http://localhost:" + serverPort + basePath;
            String subject = "Password Recovery Instructions";
            String recoveryLink = fullBasePath + "/public/token/recover-password?token=" + tokenUsuarioService.createTokenForUser(usuario, TokenType.RECOVERY);
            String message = "Dear " + usuario.getAlias() + ",\n\n" +
                    "To reset your password, please click the following link:\n" +
                    recoveryLink + "\n\n" +
                    "If you did not request a password reset, please ignore this email.";

            emailService.sendSimpleEmail(usuario.getCorreo(), subject, message);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Async
    private void notifyPurchase(Usuario usuario, CompraDTO compraDTO, boolean success) {
        String subject = "Purchase" + (success? "Confirmation" : "Rejected") + " - Order #" + compraDTO.getCodigoOrden();
        String message = "Dear " + usuario.getAlias() + ",\n\n" +
                "Thank you for your purchase. Your order details are as follows:\n" +
                compraDTO + "\n\n" +
                "Best regards,\nYour Company";

        emailService.sendSimpleEmail(usuario.getCorreo(), subject, message);
    }

    public Usuario save(Usuario Usuario) {
        return usuarioRepository.save(Usuario);
    }

    @Transactional
    public boolean update(Usuario usuario){
        try {
            usuarioRepository.update(usuario);
            if (usuario.getDetallesUsuario() != null){
                DetallesUsuario detallesUsuario = usuario.getDetallesUsuario();
                detallesUsuario.setAlias(usuario.getAlias());
                detallesUsuarioRepository.update(detallesUsuario);
            }
            return true;
        } catch (NoResultException e){
            return false;
        }
    }

    public boolean inhabilitar(String usuarioId){
        return obtenerUsuario(usuarioId).map(usuario -> {
            usuario.setHabilitado(false);
            usuarioRepository.update(usuario);
            return true;
        }).orElse(false);
    }

    public boolean habilitar(String usuarioId){
        return obtenerUsuario(usuarioId).map(usuario -> {
            usuario.setHabilitado(true);
            usuarioRepository.update(usuario);
            return true;
        }).orElse(false);
    }

    public boolean changePwd(UsuarioPwdRecoveryDTO usuarioPwdRecoveryDTO){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return obtenerUsuario(usuarioPwdRecoveryDTO.getAlias()).map(usuario -> {
            usuario.setContrasena(encoder.encode(usuarioPwdRecoveryDTO.getNewPwd()));
            usuarioRepository.update(usuario);
            return true;
        }).orElse(false);
    }

    public boolean delete(String usuarioId) {
        return obtenerUsuario(usuarioId).map(usuario -> {
            usuarioRepository.delete(usuario.getAlias());
            return true;
        }).orElse(false);
    }

    public void validatePaymentMethod(MetodoPagoUsuario metodoPago, double totalPrice) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yy");
        Random random = new Random();
        double max = 1000000.0, min = 20000.0;
        double simulatedFunds = random.nextDouble(max - min) + min;
        Date fechaExpiracion;
        try{
            fechaExpiracion = sdf.parse(metodoPago.getFechaExpiracion());
        } catch (ParseException e){
            try {
                sdf = new SimpleDateFormat("MM/yy");
                fechaExpiracion = sdf.parse(metodoPago.getFechaExpiracion());
            } catch (ParseException ex) {
                throw new WrongFormatException("The expiry Date have been saved with a wrong format");
            }
        }
        if (fechaExpiracion.before(new Date())) {
            throw new ExpiredCardException("The card is expired.");
        }

        if (totalPrice >simulatedFunds) {
            throw new InsufficientFundsException("Insufficient funds on the card.");
        }

        if (new Random().nextInt(100) < 30) {
            throw new RejectedPaymentException("The payment was rejected by the bank.");
        }
    }
}
