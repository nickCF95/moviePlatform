package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.Categoria;
import com.vortexBird.moviePlatform.domain.Ciudad;
import com.vortexBird.moviePlatform.persistence.entity.Category;
import com.vortexBird.moviePlatform.persistence.entity.City;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CiudadMapper {
    @Mappings({
            @Mapping(source = "idCity", target = "ciudadId"),
            @Mapping(source = "name", target = "nombre"),
    })
    Ciudad toCiudad(City city);
    List<Ciudad> toCiudades(List<City> cities);

    @InheritInverseConfiguration
    City toCity(Ciudad ciudad);
}
