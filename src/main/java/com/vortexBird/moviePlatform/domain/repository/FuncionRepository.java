package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.Funcion;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface FuncionRepository {
    Optional<List<Funcion>> obtenerTodas(boolean justEnabled);
    Optional<List<Funcion>> obtenerPorSalaCine(int salaId);
    Optional<List<Funcion>> obtenerHabilitadaPorSalaCine(int salaId);
    Optional<List<Funcion>> obtenerPorPelicula(long peliculaId);
    Optional<List<Funcion>> obtenerHabilitadaPorPelicula(long peliculaId);
    Optional<List<Funcion>> obtenerPorFecha(Timestamp fecha);
    Optional<List<Funcion>> obtenerPorFechaAntesDe(Timestamp fecha);
    Optional<List<Funcion>> obtenerPorFechaDespuesDe(Timestamp fecha);
    Optional<List<Funcion>> obtenerPorRangoFecha(Timestamp fechaI, Timestamp fechaF);
    Optional<Funcion> obtenerFuncion(long funcionId);
    Funcion save(Funcion funcion);
    void update(Funcion funcion);
    void delete(long funcionId);
}
