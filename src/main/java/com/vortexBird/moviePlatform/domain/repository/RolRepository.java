package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.Rol;

import java.util.List;
import java.util.Optional;

public interface RolRepository {
    Optional<List<Rol>> obtenerTodos();
    Optional<List<Rol>> obtenerPorCoincidenciaNombre(String matchStr);
    Optional<Rol> obtenerRol(int rolId);
    Optional<Rol> obtenerPorNombre(String nombre);
    Rol save(Rol rol);
    void delete(int rolId);
}
