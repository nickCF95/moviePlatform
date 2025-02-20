package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.Categoria;
import com.vortexBird.moviePlatform.domain.Pelicula;
import com.vortexBird.moviePlatform.persistence.entity.Category;
import com.vortexBird.moviePlatform.persistence.entity.Movie;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",uses = {CategoriaMapper.class})
public interface PeliculaMapper {
    @Mappings({
            @Mapping(source = "idMovie", target = "peliculaId"),
            @Mapping(source = "title", target = "titulo"),
            @Mapping(source = "categories", target = "categorias"),
            @Mapping(source = "releaseDate", target = "fechaLanzamiento"),
            @Mapping(source = "available", target = "disponible"),
            @Mapping(source = "fileNameImg", target = "nombreArchivoImagen"),
    })
    Pelicula toPelicula(Movie movie);
    List<Pelicula> toPeliculas(List<Movie> movies);

    List<Movie> toMovies(List<Pelicula> peliculas);

    @InheritInverseConfiguration
    Movie toMovie(Pelicula pelicula);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "toMovie")
    void updateMovieFromPelicula(Pelicula pelicula, @MappingTarget Movie movie);
}
