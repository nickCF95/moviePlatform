package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.Category;
import com.vortexBird.moviePlatform.persistence.entity.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationCrudRepository extends CrudRepository<Location, Integer> {
    @Query(value = "SELECT * FROM LOCATIONS WHERE NAME LIKE %:name%", nativeQuery = true)
    Optional<List<Location>> searchByNameLike(@Param("name") String matchName);

    Optional<List<Location>> findByName(String name);

    Optional<List<Location>> findByCity_idCity(int idCity);

    Optional<Location> findByNameAndCity_idCity(String name, int idCity);
}
