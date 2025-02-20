package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.DetallesUsuario;
import com.vortexBird.moviePlatform.domain.repository.DetallesUsuarioRepository;
import com.vortexBird.moviePlatform.persistence.crud.UserCrudRepository;
import com.vortexBird.moviePlatform.persistence.crud.UserDetailCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.User;
import com.vortexBird.moviePlatform.persistence.entity.UserDetails;
import com.vortexBird.moviePlatform.persistence.mapper.DetallesUsuarioMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDetailRepository implements DetallesUsuarioRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailRepository.class);

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Autowired
    private UserDetailCrudRepository userDetailCrudRepository;

    @Autowired
    private DetallesUsuarioMapper detallesUsuarioMapper;

    @Override
    public Optional<List<DetallesUsuario>> obtenerTodos() {
        return Optional.of(detallesUsuarioMapper.toDetallesUsuarios((List<UserDetails>) userDetailCrudRepository.findAll()));
    }

    @Override
    public Optional<List<DetallesUsuario>> obtenerPorFechaNacimiento(Timestamp fechaNacimiento) {
        return userDetailCrudRepository.findByDateOfBirth(fechaNacimiento).map(userDetailList -> detallesUsuarioMapper.toDetallesUsuarios(userDetailList));
    }

    @Override
    public Optional<List<DetallesUsuario>> obtenerPorFechaNacimientoAntesDe(Timestamp fechaNacimiento) {
        return userDetailCrudRepository.findByDateOfBirthBefore(fechaNacimiento).map(userDetailList -> detallesUsuarioMapper.toDetallesUsuarios(userDetailList));
    }

    @Override
    public Optional<List<DetallesUsuario>> obtenerPorFechaNacimientoDespuesDe(Timestamp fechaNacimiento) {
        return userDetailCrudRepository.findByDateOfBirthAfter(fechaNacimiento).map(userDetailList -> detallesUsuarioMapper.toDetallesUsuarios(userDetailList));
    }

    @Override
    public Optional<List<DetallesUsuario>> obtenerPorRangoDeFechas(Timestamp fechaNacimientoI, Timestamp fechaNacimientoF) {
        return userDetailCrudRepository.findByDateOfBirthBetween(fechaNacimientoI, fechaNacimientoF).map(userDetailList -> detallesUsuarioMapper.toDetallesUsuarios(userDetailList));
    }

    @Override
    public Optional<DetallesUsuario> obtenerDetallesUsuario(String usuarioId) {
        User user = new User();
        user.setNickname(usuarioId);
        return userDetailCrudRepository.findById(user).map(userDetail -> detallesUsuarioMapper.toDetallesUsuario(userDetail));
    }

    @Override
    public Optional<DetallesUsuario> obtenerPorTelefono(String numeroTelefono) {
        return userDetailCrudRepository.findByPhoneNumber(numeroTelefono).map(userDetail -> detallesUsuarioMapper.toDetallesUsuario(userDetail));
    }

    @Override
    public DetallesUsuario save(DetallesUsuario detallesUsuario) {
        return detallesUsuarioMapper.toDetallesUsuario(userDetailCrudRepository.save(detallesUsuarioMapper.toUserDetail(detallesUsuario)));
    }

    @Override
    public void update(DetallesUsuario detallesUsuario) {
        UserDetails userDetails = userDetailCrudRepository.findByNickname(detallesUsuario.getAlias()).orElse(detallesUsuarioMapper.toUserDetail(detallesUsuario));
        LOGGER.info("Detalles Usuario encontrados: " + userDetails);
        LOGGER.info("Detalles Usuario enviados: " + detallesUsuario);
        detallesUsuarioMapper.updateUserDetailsFromDetallesUsuario(detallesUsuario, userDetails);
        userDetails.setUser(userCrudRepository.findById(detallesUsuario.getAlias()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        LOGGER.info("Detalles Usuario actualizados: " + userDetails);
        userDetailCrudRepository.save(userDetails);
    }

    @Override
    public void delete(String usuarioId) {
        User user = new User();
        user.setNickname(usuarioId);
        userDetailCrudRepository.deleteById(user);
    }
}
