package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.TokenUsuario;

import java.util.List;
import java.util.Optional;

public interface TokenUsuarioRepository {
    Optional<List<TokenUsuario>> obtenerTodos();
    Optional<TokenUsuario> obtenerPorId(long tokenId);
    Optional<TokenUsuario> obtenerPorToken(String token);
    TokenUsuario save(TokenUsuario tokenUsuario);
    void delete(long id);
}
