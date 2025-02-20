package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.Funcion;
import com.vortexBird.moviePlatform.domain.repository.FuncionRepository;
import com.vortexBird.moviePlatform.persistence.entity.Function;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",uses = {PeliculaMapper.class, SalaCineMapper.class})
public interface FuncionMapper {
    @Mappings({
            @Mapping(source = "idFunction", target = "funcionId"),
            @Mapping(source = "date", target = "fecha"),
            @Mapping(source = "movie", target = "pelicula"),
            @Mapping(source = "cinemaRoom", target = "salaCine"),
            @Mapping(source = "enable", target = "habilitada"),
    })
    Funcion toFuncion(Function function);

    List<Funcion> toFunciones(List<Function> functions);

    @InheritInverseConfiguration
    Function toFunction(Funcion funcion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "toFunction")
    void updateFunctionFromFuncion(Funcion funcion, @MappingTarget Function function);
}
