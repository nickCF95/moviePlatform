package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.Silla;
import com.vortexBird.moviePlatform.persistence.entity.Seat;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses = {SalaCineMapper.class})
public interface SillaMapper {
    @Mappings({
            @Mapping(source = "idSeat", target = "sillaId"),
            @Mapping(source = "codeSeat", target = "codigoSilla"),
            @Mapping(source = "cinemaRoom", target = "salaCine"),
    })
    Silla toSilla(Seat seat);

    List<Silla> toSillas(List<Seat> seats);

    @InheritInverseConfiguration
    Seat toSeat(Silla silla);
}
