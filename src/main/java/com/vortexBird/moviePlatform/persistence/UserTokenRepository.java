package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.TokenUsuario;
import com.vortexBird.moviePlatform.domain.repository.TokenUsuarioRepository;
import com.vortexBird.moviePlatform.domain.service.UsuarioService;
import com.vortexBird.moviePlatform.persistence.crud.UserCrudRepository;
import com.vortexBird.moviePlatform.persistence.crud.UserTokenCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.User;
import com.vortexBird.moviePlatform.persistence.entity.UserToken;
import com.vortexBird.moviePlatform.persistence.mapper.TokenUsuarioMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserTokenRepository implements TokenUsuarioRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenRepository.class);

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Autowired
    private UserTokenCrudRepository userTokenCrudRepository;

    @Autowired
    private TokenUsuarioMapper tokenUsuarioMapper;

    @Override
    public Optional<List<TokenUsuario>> obtenerTodos() {
        return Optional.of(tokenUsuarioMapper.toTokensUsuarios((List<UserToken>) userTokenCrudRepository.findAll()));
    }

    @Override
    public Optional<TokenUsuario> obtenerPorId(long tokenId) {
        return userTokenCrudRepository.findById(tokenId).map(userToken -> tokenUsuarioMapper.toTokenUsuario(userToken));
    }

    @Override
    public Optional<TokenUsuario> obtenerPorToken(String token) {
        return userTokenCrudRepository.findByToken(token).map(userToken -> {
                LOGGER.info("Token: "+ userToken);
                return tokenUsuarioMapper.toTokenUsuario(userToken);
        });
    }

    @Override
    public TokenUsuario save(TokenUsuario tokenUsuario) {
        User user = userCrudRepository.findById(tokenUsuario.getUsuario().getAlias()).orElseThrow(EntityNotFoundException::new);
        UserToken userToken = tokenUsuarioMapper.toUserToken(tokenUsuario);
        userToken.setUser(user);
        return tokenUsuarioMapper.toTokenUsuario(userTokenCrudRepository.save(userToken));
    }

    @Override
    public void delete(long id) {
        userTokenCrudRepository.deleteById(id);
    }
}
