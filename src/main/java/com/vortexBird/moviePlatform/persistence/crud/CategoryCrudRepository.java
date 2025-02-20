package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface CategoryCrudRepository extends CrudRepository<Category, Integer> {
    @Query(value = "SELECT * FROM CATEGORIES WHERE NAME LIKE %:name%", nativeQuery = true)
    Optional<List<Category>> searchByNameLike(@Param("name") String matchName);

    Optional<Category> findByName(String name);
}
