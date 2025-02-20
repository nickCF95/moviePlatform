package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.MetodoPagoUsuario;
import com.vortexBird.moviePlatform.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface MetodoPagoUsuarioRepository {
    Optional<List<MetodoPagoUsuario>> obtenerTodo();
    Optional<List<MetodoPagoUsuario>> obtenerPorUsuario(String alias);
    Optional<List<MetodoPagoUsuario>>obtenerPorCoincidenciaUsuario(String matchStr);
    Optional<MetodoPagoUsuario> obtenerMetodoPagoUsuario(long metodoPagoUsuarioId);
    MetodoPagoUsuario save(MetodoPagoUsuario metodoPagoUsuario);
    void update(MetodoPagoUsuario metodoPagoUsuario);
    void delete(long metodoPagoUsuarioId);
}
