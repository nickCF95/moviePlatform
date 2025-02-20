package com.vortexBird.moviePlatform.persistence.mapper;


import com.vortexBird.moviePlatform.domain.Usuario;
import com.vortexBird.moviePlatform.persistence.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",uses = {RolMapper.class})
public interface UsuarioMapper {
    @Mappings({
            @Mapping(source = "nickname", target = "alias"),
            @Mapping(source = "password", target = "contrasena"),
            @Mapping(source = "email", target = "correo"),
            @Mapping(source = "roles", target = "roles"),
            @Mapping(source = "enable", target = "habilitado"),
    })
    Usuario toUsuario(User user);

    List<Usuario> toUsuarios(List<User> users);

    @InheritInverseConfiguration
    User toUser(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "toUser")
    void updateUserFromUsuario(Usuario usuario, @MappingTarget User user);
}
