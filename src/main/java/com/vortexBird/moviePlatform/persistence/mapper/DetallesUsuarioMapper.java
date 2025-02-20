package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.DetallesUsuario;
import com.vortexBird.moviePlatform.persistence.entity.UserDetails;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface DetallesUsuarioMapper {
    @Mappings({
            @Mapping(source = "fullName", target = "nombreCompleto"),
            @Mapping(source = "dateOfBirth", target = "fechaNacimiento"),
            @Mapping(source = "address", target = "direccion"),
            @Mapping(source = "phoneNumber", target = "numeroTelefono"),
            @Mapping(target = "alias", ignore = true),
    })
    DetallesUsuario toDetallesUsuario(UserDetails userDetail);

    List<DetallesUsuario> toDetallesUsuarios(List<UserDetails> userDetailList);

    @InheritInverseConfiguration
    @Mapping(target = "user", ignore = true)
    UserDetails toUserDetail(DetallesUsuario detallesUsuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "toUserDetail")
    @Mapping(target = "user", ignore = true)
    void updateUserDetailsFromDetallesUsuario(DetallesUsuario detallesUsuario, @MappingTarget UserDetails userDetail);
}
