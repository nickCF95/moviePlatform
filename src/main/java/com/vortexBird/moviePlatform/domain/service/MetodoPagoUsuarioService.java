package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.MetodoPagoUsuario;
import com.vortexBird.moviePlatform.domain.repository.MetodoPagoUsuarioRepository;
import com.vortexBird.moviePlatform.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.vortexBird.moviePlatform.web.utils.SecurityUtils.validateUser;

@Service
public class MetodoPagoUsuarioService {
    @Autowired
    private MetodoPagoUsuarioRepository metodoPagoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<MetodoPagoUsuario> obtenerTodo() {
        List<MetodoPagoUsuario> emptyList = new ArrayList<>();
        return metodoPagoUsuarioRepository.obtenerTodo().orElse(emptyList);
    }

    public Optional<MetodoPagoUsuario> obtenerMetodoPagoUsuario(long metodoPagoUsuarioId) {
        return metodoPagoUsuarioRepository.obtenerMetodoPagoUsuario(metodoPagoUsuarioId);
    }

    public Optional<List<MetodoPagoUsuario>> obtenerMetodosPagoPorUsuario(String alias) {
        return metodoPagoUsuarioRepository.obtenerPorUsuario(alias);
    }

    public MetodoPagoUsuario save(MetodoPagoUsuario metodoPagoUsuario) {
        validateUser(metodoPagoUsuario.getUsuario().getAlias());
        metodoPagoUsuario.setHabilitado(true);
        return metodoPagoUsuarioRepository.save(metodoPagoUsuario);
    }

    public boolean update(MetodoPagoUsuario metodoPagoUsuario){
        validateUser(metodoPagoUsuario.getUsuario().getAlias());
        try {
            metodoPagoUsuarioRepository.update(metodoPagoUsuario);
            return true;
        } catch (NoResultException e){
            return false;
        }
    }

    public boolean inhabilitar(int metodoPagoUsuarioId){
        MetodoPagoUsuario metodoPagoUsuario = metodoPagoUsuarioRepository.obtenerMetodoPagoUsuario(metodoPagoUsuarioId).orElseThrow();
        validateUser(metodoPagoUsuario.getUsuario().getAlias());
        return obtenerMetodoPagoUsuario(metodoPagoUsuarioId).map(metodoPagoUsuarioInner -> {
            metodoPagoUsuarioInner.setHabilitado(false);
            metodoPagoUsuarioRepository.update(metodoPagoUsuarioInner);
            return true;
        }).orElse(false);
    }

    public boolean delete(int metodoPagoUsuarioId) {
        MetodoPagoUsuario metodoPagoUsuario = metodoPagoUsuarioRepository.obtenerMetodoPagoUsuario(metodoPagoUsuarioId).orElseThrow();
        validateUser(metodoPagoUsuario.getUsuario().getAlias());
        return obtenerMetodoPagoUsuario(metodoPagoUsuarioId).map(metodoPagoUsuarioInner -> {
            metodoPagoUsuarioRepository.delete(metodoPagoUsuarioInner.getMetodoPagoUsuarioId());
            return true;
        }).orElse(false);
    }

}
