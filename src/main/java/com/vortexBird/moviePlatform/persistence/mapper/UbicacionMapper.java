package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.Ubicacion;
import com.vortexBird.moviePlatform.persistence.entity.Location;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses = {CiudadMapper.class})
public interface UbicacionMapper {
    @Mappings({
            @Mapping(source = "idLocation", target = "ubicacionId"),
            @Mapping(source = "name", target = "nombre"),
            @Mapping(source = "address", target = "direccion"),
            @Mapping(source = "city", target = "ciudad"),
    })
    Ubicacion toUbicacion(Location location);

    List<Ubicacion> toUbicaciones(List<Location> locations);

    @InheritInverseConfiguration
    Location toLocation(Ubicacion ubicacion);
}
