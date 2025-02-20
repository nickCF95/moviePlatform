package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.Categoria;
import com.vortexBird.moviePlatform.domain.repository.CategoriaRepository;
import com.vortexBird.moviePlatform.persistence.crud.CategoryCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.Category;
import com.vortexBird.moviePlatform.persistence.mapper.CategoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepository implements CategoriaRepository {

    @Autowired
    CategoryCrudRepository categoryCrudRepository;

    @Autowired
    CategoriaMapper categoriaMapper;

    @Override
    public Optional<List<Categoria>> obtenerTodas() {
        return Optional.of(categoriaMapper.toCategorias((List<Category>) categoryCrudRepository.findAll()));
    }

    @Override
    public Optional<List<Categoria>> obtenerPorCoincidenciaNombre(String matchStr) {
        return categoryCrudRepository.searchByNameLike(matchStr).map(categories -> categoriaMapper.toCategorias(categories));
    }

    @Override
    public Optional<Categoria> obtenerCategoria(int categoriaId) {
        return categoryCrudRepository.findById(categoriaId).map(category -> categoriaMapper.toCategoria(category));
    }

    @Override
    public Optional<Categoria> obtenerPorNombre(String nombre) {
        return categoryCrudRepository.findByName(nombre).map(category -> categoriaMapper.toCategoria(category));
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaMapper.toCategoria(categoryCrudRepository.save(categoriaMapper.toCategory(categoria)));
    }

    @Override
    public void delete(int categoriaId) {
        categoryCrudRepository.deleteById(categoriaId);
    }
}
