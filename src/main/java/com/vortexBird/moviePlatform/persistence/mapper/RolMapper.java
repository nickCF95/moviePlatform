package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.Rol;
import com.vortexBird.moviePlatform.persistence.entity.Role;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {
    @Mappings({
            @Mapping(source = "idRole", target = "rolId"),
            @Mapping(source = "name", target = "nombre"),
    })
    Rol toRol(Role role);

    List<Rol> toRoles(List<Role> rolls);

    @InheritInverseConfiguration
    Role toRole(Rol rol);


}
