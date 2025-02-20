package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.Pelicula;
import com.vortexBird.moviePlatform.domain.repository.PeliculaRepository;
import com.vortexBird.moviePlatform.persistence.crud.MovieCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.Movie;
import com.vortexBird.moviePlatform.persistence.entity.Ticket;
import com.vortexBird.moviePlatform.persistence.mapper.PeliculaMapper;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepository implements PeliculaRepository {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Autowired
    private PeliculaMapper peliculaMapper;

    @Override
    public Optional<List<Pelicula>> obtenerTodas(boolean justEnabled) {
        if(justEnabled){
            Specification<Movie> spec = (root, query, cb) -> cb.equal(root.get("available"), true);
            return Optional.of(peliculaMapper.toPeliculas( movieCrudRepository.findAll(spec)));
        } else {
            return Optional.of(peliculaMapper.toPeliculas((List<Movie>) movieCrudRepository.findAll()));
        }
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorCualquierCoincidencia(String keyword, boolean justEnabled) {
        String pattern = "%" + keyword.toLowerCase() + "%";

        Specification<Movie> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.like(cb.lower(root.get("title")), pattern));
            predicates.add(cb.like(cb.lower(root.get("director")), pattern));

            // Note: This example uses MySQL's DATE_FORMAT function.
            predicates.add(
                    cb.like(
                            cb.function("DATE_FORMAT", String.class, root.get("releaseDate"), cb.literal("%Y-%m-%d")),
                            pattern
                    )
            );

            Join<Object, Object> categoryJoin = root.join("categories", JoinType.LEFT);
            predicates.add(cb.like(cb.lower(categoryJoin.get("name")), pattern));

            Predicate searchFieldsPredicate = cb.or(predicates.toArray(new Predicate[0]));
            if(justEnabled){
                Predicate availablePredicate = cb.equal(root.get("available"), true);
                return cb.and(availablePredicate, searchFieldsPredicate);
            } else {
                return searchFieldsPredicate;
            }
        };
        return Optional.of(peliculaMapper.toPeliculas(movieCrudRepository.findAll(spec)));
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorCategoria(int categoriaId) {
        return movieCrudRepository.findByCategories_idCategory(categoriaId).map(movies -> peliculaMapper.toPeliculas(movies));
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorDirector(String director) {
        return movieCrudRepository.findByDirector(director).map(movies -> peliculaMapper.toPeliculas(movies));
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorCoincidenciaTitulo(String matchStr) {
        return movieCrudRepository.searchByTitleLike(matchStr).map(movies -> peliculaMapper.toPeliculas(movies));
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorCoincidenciaDirector(String matchStr) {
        return movieCrudRepository.searchByDirectorLike(matchStr).map(movies -> peliculaMapper.toPeliculas(movies));
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorFechaLanzamiento(Timestamp fechaLanzamiento) {
        return movieCrudRepository.findByReleaseDate(fechaLanzamiento).map(movies -> peliculaMapper.toPeliculas(movies));
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorFechaLanzamientoAntesDe(Timestamp fechaLanzamiento) {
        return movieCrudRepository.findByReleaseDateBefore(fechaLanzamiento).map(movies -> peliculaMapper.toPeliculas(movies));
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorFechaLanzamientoDespuesDe(Timestamp fechaLanzamiento) {
        return movieCrudRepository.findByReleaseDateAfter(fechaLanzamiento).map(movies -> peliculaMapper.toPeliculas(movies));
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorRangoDeFechas(Timestamp fechaLanzamientoI, Timestamp fechaLanzamientoF) {
        return movieCrudRepository.findByReleaseDateBetween(fechaLanzamientoI, fechaLanzamientoF).map(movies -> peliculaMapper.toPeliculas(movies));
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorDisponibilidad(Boolean disponible) {
        return movieCrudRepository.findByAvailable(disponible).map(movies -> peliculaMapper.toPeliculas(movies));
    }

    @Override
    public Optional<Pelicula> obtenerPelicula(long peliculaId) {
        return movieCrudRepository.findById(peliculaId).map(movie -> peliculaMapper.toPelicula(movie));
    }

    @Override
    public Optional<List<Pelicula>> obtenerPorTitulo(String title) {
        return movieCrudRepository.findByTitle(title).map(movies -> peliculaMapper.toPeliculas(movies));
    }

    @Override
    public List<Pelicula> saveAll(List<Pelicula> peliculas) {
        return peliculaMapper.toPeliculas((List<Movie>) movieCrudRepository.saveAll(peliculaMapper.toMovies(peliculas)));

    }

    @Override
    public Pelicula save(Pelicula pelicula) {
        return peliculaMapper.toPelicula(movieCrudRepository.save(peliculaMapper.toMovie(pelicula)));
    }

    @Override
    public void update(Pelicula pelicula) {
        Movie movie = movieCrudRepository.findById(pelicula.getPeliculaId()).orElseThrow(NoResultException::new);
        peliculaMapper.updateMovieFromPelicula(pelicula, movie);
        movieCrudRepository.save(movie);
    }

    @Override
    public void delete(long peliculaId) {
        movieCrudRepository.deleteById(peliculaId);
    }
}
