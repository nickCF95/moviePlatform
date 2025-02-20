package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {
    Optional<List<Categoria>> obtenerTodas();
    Optional<List<Categoria>> obtenerPorCoincidenciaNombre(String matchStr);
    Optional<Categoria> obtenerCategoria(int categoriaId);
    Optional<Categoria> obtenerPorNombre(String nombre);
    Categoria save(Categoria categoria);
    void delete(int categoriaId);
}
