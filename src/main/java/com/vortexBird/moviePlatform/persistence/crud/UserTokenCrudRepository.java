package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.UserToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserTokenCrudRepository extends CrudRepository<UserToken, Long> {
    Optional<UserToken> findByToken(String token);
}
