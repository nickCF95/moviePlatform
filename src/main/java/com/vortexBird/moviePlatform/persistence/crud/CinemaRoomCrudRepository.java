package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.CinemaRoom;
import com.vortexBird.moviePlatform.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CinemaRoomCrudRepository extends CrudRepository<CinemaRoom, Integer>, JpaSpecificationExecutor<CinemaRoom> {

    Optional<List<CinemaRoom>> findByLocation_idLocation(int idLocation);
    Optional<List<CinemaRoom>> findByRoomNumber(short roomNumber);
    Optional<List<CinemaRoom>> findByCapacity(int capacity);
    Optional<List<CinemaRoom>> findByCapacityLessThanEqual(int capacity);
    Optional<List<CinemaRoom>> findByCapacityGreaterThanEqual(int capacity);
    @Query(value = "SELECT * FROM CINEMA_ROOMS WHERE CAPACITY >= :capacityI AND CAPACITY <= :capacityF", nativeQuery = true)
    Optional<List<CinemaRoom>> findByCapacityRange(@Param("capacityI")int capacityI, @Param("capacityF") int capacityF);
    Optional<CinemaRoom> findByRoomNumberAndLocation_idLocation(short roomNumber, int idLocation);
}
