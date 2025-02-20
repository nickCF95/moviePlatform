package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.UserDetails;
import com.vortexBird.moviePlatform.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserDetailCrudRepository extends CrudRepository<UserDetails, User> {
    Optional<UserDetails> findByNickname(String nickname);
    Optional<UserDetails> findByPhoneNumber(String phoneNumber);
    Optional<List<UserDetails>> findByDateOfBirth(Timestamp dateOfBirth);
    Optional<List<UserDetails>> findByDateOfBirthBefore(Timestamp dateOfBirth);
    Optional<List<UserDetails>> findByDateOfBirthAfter(Timestamp dateOfBirth);
    Optional<List<UserDetails>> findByDateOfBirthBetween(Timestamp dateOfBirthI, Timestamp dateOfBirthF);
}
