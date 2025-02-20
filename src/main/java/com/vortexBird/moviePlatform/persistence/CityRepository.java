package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.Ciudad;
import com.vortexBird.moviePlatform.domain.repository.CiudadRepository;
import com.vortexBird.moviePlatform.persistence.crud.CityCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.Category;
import com.vortexBird.moviePlatform.persistence.entity.City;
import com.vortexBird.moviePlatform.persistence.mapper.CiudadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CityRepository implements CiudadRepository {
    @Autowired
    private CityCrudRepository cityCrudRepository;

    @Autowired
    private CiudadMapper ciudadMapper;

    @Override
    public Optional<List<Ciudad>> obtenerTodas() {
        return Optional.of(ciudadMapper.toCiudades((List<City>) cityCrudRepository.findAll()));
    }

    @Override
    public Optional<List<Ciudad>> obtenerPorCoincidenciaNombre(String matchStr) {
        return cityCrudRepository.searchByNameLike(matchStr).map(cities -> ciudadMapper.toCiudades(cities));

    }

    @Override
    public Optional<Ciudad> obtenerCiudad(int ciudadId) {
        return cityCrudRepository.findById(ciudadId).map(city -> ciudadMapper.toCiudad(city));
    }

    @Override
    public Optional<Ciudad> obtenerPorNombre(String nombre) {
        return cityCrudRepository.findByName(nombre).map(city -> ciudadMapper.toCiudad(city));

    }

    @Override
    public Ciudad save(Ciudad ciudad) {
        return ciudadMapper.toCiudad(cityCrudRepository.save(ciudadMapper.toCity(ciudad)));
    }

    @Override
    public void delete(int ciudadId) {
        cityCrudRepository.deleteById(ciudadId);
    }
}
