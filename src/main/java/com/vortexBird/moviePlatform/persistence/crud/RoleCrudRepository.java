package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleCrudRepository extends CrudRepository<Role, Integer> {
    @Query(value = "SELECT * FROM ROLLS WHERE NAME LIKE %:name%", nativeQuery = true)
    Optional<List<Role>> searchByNameLike(@Param("name") String matchName);

    Optional<Role> findByName(String name);
}
