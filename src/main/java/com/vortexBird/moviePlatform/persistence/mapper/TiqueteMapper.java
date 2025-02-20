package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.Tiquete;
import com.vortexBird.moviePlatform.domain.Usuario;
import com.vortexBird.moviePlatform.persistence.entity.Ticket;
import com.vortexBird.moviePlatform.persistence.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",uses = {SillaMapper.class, FuncionMapper.class})
public interface TiqueteMapper {
    @Mappings({
            @Mapping(source = "idTicket", target = "tiqueteId"),
            @Mapping(source = "function", target = "funcion"),
            @Mapping(source = "seat", target = "silla"),
            @Mapping(source = "available", target = "disponible"),
            @Mapping(source = "individualPrice", target = "precioIndividual")
    })
    Tiquete toTiquete(Ticket ticket);

    List<Tiquete> toTiquetes(List<Ticket> tickets);

    List<Ticket> toTickets(List<Tiquete> tiquetes);

    @InheritInverseConfiguration
    Ticket toTicket(Tiquete tiquete);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "toTicket")
    void updateTicketFromTiquete(Tiquete tiquete, @MappingTarget Ticket ticket);
}
