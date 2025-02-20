package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.Seat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SeatCrudRepository extends CrudRepository<Seat, Integer> {
    Optional<List<Seat>> findByCinemaRoom_idRoom(int idRoom);
    Optional<Seat> findByCodeSeatAndCinemaRoom_idRoom(String codeSeat, int idRoom);
}
