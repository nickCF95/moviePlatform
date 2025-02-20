package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.Ciudad;

import java.util.List;
import java.util.Optional;

public interface CiudadRepository {
    Optional<List<Ciudad>> obtenerTodas();
    Optional<List<Ciudad>> obtenerPorCoincidenciaNombre(String matchStr);
    Optional<Ciudad> obtenerCiudad(int ciudadId);
    Optional<Ciudad> obtenerPorNombre(String nombre);
    Ciudad save(Ciudad ciudad);
    void delete(int ciudadId);

}
