package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.Pelicula;
import com.vortexBird.moviePlatform.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<List<Usuario>> obtenerTodos();
    Optional<List<Usuario>> obtenerPorRol(int rolId);
    Optional<List<Usuario>> obtenerPorCoincidenciaAlias(String matchStr);
    Optional<List<Usuario>> obtenerPorCoincidenciaCorreo(String matchStr);
    Optional<List<Usuario>> obtenerPorCualquierCoincidencia(String keyword);
    Optional<Usuario> obtenerPorAlias(String alias);
    Optional<Usuario> obtenerPorCorreo(String correo);
    Usuario save(Usuario usuario);
    void update(Usuario usuario);
    void delete(String alias);
}
