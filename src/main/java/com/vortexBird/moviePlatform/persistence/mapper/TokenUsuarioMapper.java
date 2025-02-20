package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.TokenUsuario;
import com.vortexBird.moviePlatform.persistence.entity.UserToken;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses = {UsuarioMapper.class})
public interface TokenUsuarioMapper {
    @Mappings({
            @Mapping(source = "type", target = "tipo"),
            @Mapping(source = "user", target = "usuario"),
            @Mapping(source = "expiryDate", target = "fechaExpiracion")
    })
    TokenUsuario toTokenUsuario(UserToken userToken);

    List<TokenUsuario> toTokensUsuarios(List<UserToken> userTokens);

    @InheritInverseConfiguration
    UserToken toUserToken(TokenUsuario tokenUsuario);
}
