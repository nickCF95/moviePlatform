package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.SalaCine;
import com.vortexBird.moviePlatform.domain.repository.SalaCineRepository;
import com.vortexBird.moviePlatform.persistence.crud.CinemaRoomCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.CinemaRoom;
import com.vortexBird.moviePlatform.persistence.mapper.SalaCineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CinemaRoomRepository implements SalaCineRepository {

    @Autowired
    private CinemaRoomCrudRepository cinemaRoomCrudRepository;

    @Autowired
    private SalaCineMapper salaCineMapper;


    @Override
    public Optional<List<SalaCine>> obtenerTodas() {
        return Optional.of(salaCineMapper.toSalasCine((List<CinemaRoom>) cinemaRoomCrudRepository.findAll()));
    }

    @Override
    public Optional<List<SalaCine>> obtenerPorNumeroSala(short numeroSala) {
        return cinemaRoomCrudRepository.findByRoomNumber(numeroSala).map(cinemaRooms -> salaCineMapper.toSalasCine(cinemaRooms));
    }

    @Override
    public Optional<List<SalaCine>> obtenerPorUbicacion(int ubicacionId) {
        return cinemaRoomCrudRepository.findByLocation_idLocation(ubicacionId).map(cinemaRooms -> salaCineMapper.toSalasCine(cinemaRooms));
    }

    @Override
    public Optional<List<SalaCine>> ObtenerPorCapacidad(int capacidad) {
        return cinemaRoomCrudRepository.findByCapacity(capacidad).map(cinemaRooms -> salaCineMapper.toSalasCine(cinemaRooms));
    }

    @Override
    public Optional<List<SalaCine>> ObtenerPorCapacidadMayorA(int capacidad) {
        return cinemaRoomCrudRepository.findByCapacityGreaterThanEqual(capacidad).map(cinemaRooms -> salaCineMapper.toSalasCine(cinemaRooms));
    }

    @Override
    public Optional<List<SalaCine>> ObtenerPorCapacidadMenorA(int capacidad) {
        return cinemaRoomCrudRepository.findByCapacityLessThanEqual(capacidad).map(cinemaRooms -> salaCineMapper.toSalasCine(cinemaRooms));
    }

    @Override
    public Optional<List<SalaCine>> ObtenerPorRangoCapacidad(int capacidadI, int capacidadF) {
        return cinemaRoomCrudRepository.findByCapacityRange(capacidadI, capacidadF).map(cinemaRooms -> salaCineMapper.toSalasCine(cinemaRooms));
    }

    @Override
    public Optional<SalaCine> obtenerSalaCine(int salaId) {
        return cinemaRoomCrudRepository.findById(salaId).map(cinemaRoom -> salaCineMapper.toSalaCine(cinemaRoom));
    }

    @Override
    public Optional<SalaCine> obtenerPorNumeroSalaYUbicacion(short numeroSala, int ubicacionId) {
        return cinemaRoomCrudRepository.findByRoomNumberAndLocation_idLocation(numeroSala, ubicacionId).map(cinemaRoom -> salaCineMapper.toSalaCine(cinemaRoom));
    }

    @Override
    public SalaCine save(SalaCine salaCine) {
        return salaCineMapper.toSalaCine(cinemaRoomCrudRepository.save(salaCineMapper.toCinemaRoom(salaCine)));
    }

    @Override
    public void delete(int salaId) {
        cinemaRoomCrudRepository.deleteById(salaId);
    }
}
