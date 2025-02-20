package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.SalaCine;
import com.vortexBird.moviePlatform.persistence.entity.CinemaRoom;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses = {UbicacionMapper.class})
public interface SalaCineMapper {
    @Mappings({
            @Mapping(source = "idRoom", target = "salaId"),
            @Mapping(source = "capacity", target = "capacidad"),
            @Mapping(source = "roomNumber", target = "numeroSala"),
            @Mapping(source = "location", target = "ubicacion"),
            @Mapping(source = "enable", target = "habilitada"),
            @Mapping(source = "basePrice", target = "precioBase"),
    })
    SalaCine toSalaCine(CinemaRoom cinemaRoom);

    List<SalaCine> toSalasCine(List<CinemaRoom> cinemaRooms);

    @InheritInverseConfiguration
    CinemaRoom toCinemaRoom(SalaCine salaCine);
}
