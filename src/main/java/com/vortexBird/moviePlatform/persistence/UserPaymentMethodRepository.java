package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.MetodoPagoUsuario;
import com.vortexBird.moviePlatform.domain.repository.MetodoPagoUsuarioRepository;
import com.vortexBird.moviePlatform.persistence.crud.UserPaymentMethodCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.User;
import com.vortexBird.moviePlatform.persistence.entity.UserPaymentMethod;
import com.vortexBird.moviePlatform.persistence.mapper.MetodoPagoUsuarioMapper;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserPaymentMethodRepository implements MetodoPagoUsuarioRepository {
    @Autowired
    private UserPaymentMethodCrudRepository userPaymentMethodCrudRepository;

    @Autowired
    private MetodoPagoUsuarioMapper metodoPagoUsuarioMapper;

    @Override
    public Optional<List<MetodoPagoUsuario>> obtenerTodo() {
        return Optional.of(metodoPagoUsuarioMapper.toMetodosPagoUsuarios((List<UserPaymentMethod>) userPaymentMethodCrudRepository.findAll()));
    }

    @Override
    public Optional<List<MetodoPagoUsuario>> obtenerPorUsuario(String alias) {
        return userPaymentMethodCrudRepository.findByUser_nickname(alias).map(userPaymentMethods -> metodoPagoUsuarioMapper.toMetodosPagoUsuarios(userPaymentMethods));
    }

    @Override
    public Optional<List<MetodoPagoUsuario>> obtenerPorCoincidenciaUsuario(String matchStr) {
        return userPaymentMethodCrudRepository.searchByUserLike(matchStr).map(userPaymentMethods -> metodoPagoUsuarioMapper.toMetodosPagoUsuarios(userPaymentMethods));
    }

    @Override
    public Optional<MetodoPagoUsuario> obtenerMetodoPagoUsuario(long metodoPagoUsuarioId) {
        return userPaymentMethodCrudRepository.findById(metodoPagoUsuarioId).map(userPaymentMethod -> metodoPagoUsuarioMapper.toMetodoPagoUsuario(userPaymentMethod));
    }

    @Override
    public MetodoPagoUsuario save(MetodoPagoUsuario metodoPagoUsuario) {
        return metodoPagoUsuarioMapper.toMetodoPagoUsuario(userPaymentMethodCrudRepository.save(metodoPagoUsuarioMapper.toUserPaymentMethod(metodoPagoUsuario)));
    }

    @Override
    public void update(MetodoPagoUsuario metodoPagoUsuario) {
        UserPaymentMethod userPaymentMethod = userPaymentMethodCrudRepository.findById(metodoPagoUsuario.getMetodoPagoUsuarioId()).orElseThrow(NoResultException::new);
        metodoPagoUsuarioMapper.updateUserPaymentMethodFromMetodoPagoUsuario(metodoPagoUsuario, userPaymentMethod);
        userPaymentMethodCrudRepository.save(userPaymentMethod);
    }

    @Override
    public void delete(long metodoPagoUsuarioId) {
        userPaymentMethodCrudRepository.deleteById(metodoPagoUsuarioId);
    }
}
