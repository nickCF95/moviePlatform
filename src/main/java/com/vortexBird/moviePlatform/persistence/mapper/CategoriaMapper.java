package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.Categoria;
import com.vortexBird.moviePlatform.persistence.entity.Category;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    @Mappings({
            @Mapping(source = "idCategory", target = "categoriaId"),
            @Mapping(source = "name", target = "nombre"),
            @Mapping(source = "description", target = "descripcion"),
    })
    Categoria toCategoria(Category category);
    List<Categoria> toCategorias(List<Category> categories);

    @InheritInverseConfiguration
    Category toCategory(Categoria categoria);
}
