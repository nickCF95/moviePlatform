package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.Function;
import com.vortexBird.moviePlatform.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface FunctionCrudRepository extends CrudRepository<Function, Long>, JpaSpecificationExecutor<Function> {
    Optional<List<Function>> findByDate(Timestamp Date);
    Optional<List<Function>> findByDateBefore(Timestamp Date);
    Optional<List<Function>> findByDateAfter(Timestamp Date);
    Optional<List<Function>> findByDateBetween(Timestamp DateI, Timestamp DateF);
    Optional<List<Function>> findByMovie_idMovie(long idMovie);
    Optional<List<Function>> findByEnableTrueAndMovie_idMovie(long idMovie);
    Optional<List<Function>> findByCinemaRoom_idRoom(int idRoom);
    Optional<List<Function>> findByEnableTrueAndCinemaRoom_idRoom(int idRoom);
}
