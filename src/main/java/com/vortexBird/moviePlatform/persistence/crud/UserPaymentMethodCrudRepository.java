package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.Category;
import com.vortexBird.moviePlatform.persistence.entity.UserPaymentMethod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserPaymentMethodCrudRepository extends CrudRepository<UserPaymentMethod, Long> {
    Optional<List<UserPaymentMethod>> findByUser_nickname(String nickname);
    @Query(value = "SELECT * FROM USER_PAYMENTS WHERE ID_USER LIKE %:nickname%", nativeQuery = true)
    Optional<List<UserPaymentMethod>> searchByUserLike(@Param("nickname") String matchName);
}
