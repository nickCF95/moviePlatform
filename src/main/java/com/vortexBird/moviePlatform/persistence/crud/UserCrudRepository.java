package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.Movie;
import com.vortexBird.moviePlatform.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends CrudRepository<User, String>, JpaSpecificationExecutor<User> {
    @Query(value = "SELECT * FROM USERS WHERE NICKNAME LIKE %:nickname%", nativeQuery = true)
    Optional<List<User>> searchByNicknameLike(@Param("nickname") String matchName);
    @Query(value = "SELECT * FROM USERS WHERE EMAIL LIKE %:email%", nativeQuery = true)
    Optional<List<User>> searchByEmailLike(@Param("email") String matchEmail);
    Optional<List<User>> findByRoles_IdRole(int roleId);
    Optional<User> findByEmail(String email);
}
