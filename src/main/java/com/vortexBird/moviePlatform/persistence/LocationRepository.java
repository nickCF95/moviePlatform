package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.Ubicacion;
import com.vortexBird.moviePlatform.domain.repository.UbicacionRepository;
import com.vortexBird.moviePlatform.persistence.crud.LocationCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.Location;
import com.vortexBird.moviePlatform.persistence.mapper.UbicacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository implements UbicacionRepository {
    @Autowired
    private LocationCrudRepository locationCrudRepository;

    @Autowired
    private UbicacionMapper ubicacionMapper;

    @Override
    public Optional<List<Ubicacion>> obtenerTodas() {
        return Optional.of(ubicacionMapper.toUbicaciones((List<Location>) locationCrudRepository.findAll()));
    }

    @Override
    public Optional<List<Ubicacion>> obtenerPorCoincidenciaNombre(String matchStr) {
        return locationCrudRepository.searchByNameLike(matchStr).map(locations -> ubicacionMapper.toUbicaciones(locations));
    }

    @Override
    public Optional<List<Ubicacion>> obtenerPorNombre(String nombre) {
        return locationCrudRepository.findByName(nombre).map(locations -> ubicacionMapper.toUbicaciones(locations));
    }

    @Override
    public Optional<List<Ubicacion>> obtenerPorCiudad(int ciudadId) {
        return locationCrudRepository.findByCity_idCity(ciudadId).map(locations -> ubicacionMapper.toUbicaciones(locations));
    }

    @Override
    public Optional<Ubicacion> obtenerUbicacion(int ubicacionId) {
        return locationCrudRepository.findById(ubicacionId).map(location -> ubicacionMapper.toUbicacion(location));
    }

    @Override
    public Optional<Ubicacion> obtenerPorNombreYCiudad(String nombre, int ciudadId) {
        return locationCrudRepository.findByNameAndCity_idCity(nombre, ciudadId).map(location -> ubicacionMapper.toUbicacion(location));
    }

    @Override
    public Ubicacion save(Ubicacion ubicacion) {
        return ubicacionMapper.toUbicacion(locationCrudRepository.save(ubicacionMapper.toLocation(ubicacion)));
    }

    @Override
    public void delete(int ubicacionId) {
        locationCrudRepository.deleteById(ubicacionId);
    }
}
