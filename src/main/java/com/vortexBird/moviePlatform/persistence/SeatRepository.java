package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.Silla;
import com.vortexBird.moviePlatform.domain.repository.SillaRepository;
import com.vortexBird.moviePlatform.persistence.crud.SeatCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.Seat;
import com.vortexBird.moviePlatform.persistence.mapper.SillaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SeatRepository implements SillaRepository {

    @Autowired
    private SeatCrudRepository seatCrudRepository;

    @Autowired
    private SillaMapper sillaMapper;

    @Override
    public Optional<List<Silla>> obtenerTodas() {
        return Optional.of(sillaMapper.toSillas((List<Seat>) seatCrudRepository.findAll()));
    }

    @Override
    public Optional<List<Silla>> obtenerPorSalaCine(int salaId) {
        return seatCrudRepository.findByCinemaRoom_idRoom(salaId).map(seats -> sillaMapper.toSillas(seats));
    }

    @Override
    public Optional<Silla> obtenerSilla(int sillaId) {
        return seatCrudRepository.findById(sillaId).map(seat -> sillaMapper.toSilla(seat));
    }

    @Override
    public Optional<Silla> obtenerPorCodigoSillaYSalaCine(String codigoSilla, int salaId) {
        return seatCrudRepository.findByCodeSeatAndCinemaRoom_idRoom(codigoSilla, salaId).map(seat -> sillaMapper.toSilla(seat));
    }

    @Override
    public Silla save(Silla silla) {
        return sillaMapper.toSilla(seatCrudRepository.save(sillaMapper.toSeat(silla)));
    }

    @Override
    public void delete(int sillaId) {
        seatCrudRepository.deleteById(sillaId);
    }
}
