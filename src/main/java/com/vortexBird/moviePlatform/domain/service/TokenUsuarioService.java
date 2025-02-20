package com.vortexBird.moviePlatform.domain.service;



import com.vortexBird.moviePlatform.domain.TokenUsuario;
import com.vortexBird.moviePlatform.domain.Usuario;
import com.vortexBird.moviePlatform.persistence.UserTokenRepository;
import com.vortexBird.moviePlatform.persistence.entity.User;
import com.vortexBird.moviePlatform.persistence.entity.UserToken;
import com.vortexBird.moviePlatform.persistence.enums.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class TokenUsuarioService {

    @Autowired
    private UserTokenRepository tokenRepository;

    @Autowired
    private TokenService tokenService;


    public String createTokenForUser(Usuario usuario, TokenType type) {
        String token = tokenService.generateToken();

        TokenUsuario tokenUsuario = new TokenUsuario(token, type, usuario);

        tokenRepository.save(tokenUsuario);

        return token;
    }

    public Optional<TokenUsuario> obtenerPorToken(String token){
        return tokenRepository.obtenerPorToken(token);
    }

    public boolean delete(long id) {
        return tokenRepository.obtenerPorId(id).map(tokenUsuario -> {
            tokenRepository.delete(tokenUsuario.getId());
            return true;
        }).orElse(false);
    }
}

