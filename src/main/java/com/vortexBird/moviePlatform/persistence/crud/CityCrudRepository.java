package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.Category;
import com.vortexBird.moviePlatform.persistence.entity.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CityCrudRepository extends CrudRepository<City, Integer> {
    @Query(value = "SELECT * FROM CITIES WHERE NAME LIKE %:name%", nativeQuery = true)
    Optional<List<City>> searchByNameLike(@Param("name") String matchName);

    Optional<City> findByName(String name);
}
