package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.Movie;
import com.vortexBird.moviePlatform.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface MovieCrudRepository extends CrudRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    Optional<List<Movie>> findByCategories_idCategory(int idCategory);
    Optional<List<Movie>> findByDirector(String director);
    Optional<List<Movie>> findByTitle(String title);
    @Query(value = "SELECT * FROM MOVIES WHERE DIRECTOR LIKE %:director%", nativeQuery = true)
    Optional<List<Movie>> searchByDirectorLike(@Param("director") String matchStr);
    @Query(value = "SELECT * FROM MOVIES WHERE TITLE LIKE %:title%", nativeQuery = true)
    Optional<List<Movie>> searchByTitleLike(@Param("title") String matchStr);
    Optional<List<Movie>> findByReleaseDate(Timestamp releaseDate);
    Optional<List<Movie>> findByReleaseDateBefore(Timestamp releaseDate);
    Optional<List<Movie>> findByReleaseDateAfter(Timestamp releaseDate);
    Optional<List<Movie>> findByReleaseDateBetween(Timestamp releaseDateI, Timestamp releaseDateF);
    Optional<List<Movie>> findByAvailable(Boolean available);
}
