package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.Ubicacion;

import java.util.List;
import java.util.Optional;

public interface UbicacionRepository {
    Optional<List<Ubicacion>> obtenerTodas();
    Optional<List<Ubicacion>> obtenerPorCoincidenciaNombre(String matchStr);
    Optional<List<Ubicacion>> obtenerPorNombre(String nombre);
    Optional<List<Ubicacion>> obtenerPorCiudad(int ciudadId);
    Optional<Ubicacion> obtenerUbicacion(int ubicacionId);
    Optional<Ubicacion> obtenerPorNombreYCiudad(String nombre, int ciudadId);
    Ubicacion save(Ubicacion ubicacion);
    void delete(int ubicacionId);
}
