package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.DetallesUsuario;
import com.vortexBird.moviePlatform.domain.DetallesUsuario;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface DetallesUsuarioRepository {
    Optional<List<DetallesUsuario>> obtenerTodos();
    Optional<List<DetallesUsuario>> obtenerPorFechaNacimiento(Timestamp fechaNacimiento);
    Optional<List<DetallesUsuario>> obtenerPorFechaNacimientoAntesDe(Timestamp fechaNacimiento);
    Optional<List<DetallesUsuario>> obtenerPorFechaNacimientoDespuesDe(Timestamp fechaNacimiento);
    Optional<List<DetallesUsuario>> obtenerPorRangoDeFechas(Timestamp fechaNacimientoI, Timestamp fechaNacimientoF);
    Optional<DetallesUsuario> obtenerDetallesUsuario(String usuarioId);
    Optional<DetallesUsuario> obtenerPorTelefono(String numeroTelefono);
    DetallesUsuario save(DetallesUsuario detallesUsuario);
    void update(DetallesUsuario detallesUsuario);
    void delete(String usuarioId);
}
