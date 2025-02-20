package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.Pelicula;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PeliculaRepository {
    Optional<List<Pelicula>> obtenerTodas(boolean addAvailableFilter);
    Optional<List<Pelicula>> obtenerPorCualquierCoincidencia(String keyword, boolean addAvailableFilter);
    Optional<List<Pelicula>> obtenerPorCategoria(int categoriaId);
    Optional<List<Pelicula>> obtenerPorDirector(String director);
    Optional<List<Pelicula>> obtenerPorCoincidenciaTitulo(String matchStr);
    Optional<List<Pelicula>> obtenerPorCoincidenciaDirector(String matchStr);
    Optional<List<Pelicula>> obtenerPorFechaLanzamiento(Timestamp fechaLanzamiento);
    Optional<List<Pelicula>> obtenerPorFechaLanzamientoAntesDe(Timestamp fechaLanzamiento);
    Optional<List<Pelicula>> obtenerPorFechaLanzamientoDespuesDe(Timestamp fechaLanzamiento);
    Optional<List<Pelicula>> obtenerPorRangoDeFechas(Timestamp fechaLanzamientoI, Timestamp fechaLanzamientoF);
    Optional<List<Pelicula>> obtenerPorDisponibilidad(Boolean disponible);
    Optional<Pelicula> obtenerPelicula(long peliculaId);
    Optional<List<Pelicula>> obtenerPorTitulo(String title);
    List<Pelicula> saveAll(List<Pelicula> peliculas);
    Pelicula save(Pelicula pelicula);
    void update(Pelicula pelicula);
    void delete(long peliculaId);
}
