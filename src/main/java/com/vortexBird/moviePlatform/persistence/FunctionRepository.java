package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.Funcion;
import com.vortexBird.moviePlatform.domain.repository.FuncionRepository;
import com.vortexBird.moviePlatform.persistence.crud.FunctionCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.Function;
import com.vortexBird.moviePlatform.persistence.entity.Movie;
import com.vortexBird.moviePlatform.persistence.mapper.FuncionMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class FunctionRepository implements FuncionRepository {

    @Autowired
    private FunctionCrudRepository functionCrudRepository;

    @Autowired
    private FuncionMapper funcionMapper;

    @Override
    public Optional<List<Funcion>> obtenerTodas(boolean justEnabled) {
        if(justEnabled){
            Specification<Function> spec = (root, query, cb) -> cb.equal(root.get("enable"), true);
            return Optional.of(funcionMapper.toFunciones((List<Function>) functionCrudRepository.findAll(spec)));
        } else {
            return Optional.of(funcionMapper.toFunciones((List<Function>) functionCrudRepository.findAll()));
        }
    }

    @Override
    public Optional<List<Funcion>> obtenerPorSalaCine(int salaId) {
        return functionCrudRepository.findByCinemaRoom_idRoom(salaId).map(functions -> funcionMapper.toFunciones(functions));
    }

    @Override
    public Optional<List<Funcion>> obtenerHabilitadaPorSalaCine(int salaId) {
        return functionCrudRepository.findByEnableTrueAndCinemaRoom_idRoom(salaId).map(functions -> funcionMapper.toFunciones(functions));
    }

    @Override
    public Optional<List<Funcion>> obtenerPorPelicula(long peliculaId) {
        return functionCrudRepository.findByMovie_idMovie(peliculaId).map(functions -> funcionMapper.toFunciones(functions));
    }

    @Override
    public Optional<List<Funcion>> obtenerHabilitadaPorPelicula(long peliculaId) {
        return functionCrudRepository.findByEnableTrueAndMovie_idMovie(peliculaId).map(functions -> funcionMapper.toFunciones(functions));
    }

    @Override
    public Optional<List<Funcion>> obtenerPorFecha(Timestamp fecha) {
        return functionCrudRepository.findByDate(fecha).map(functions -> funcionMapper.toFunciones(functions));
    }

    @Override
    public Optional<List<Funcion>> obtenerPorFechaAntesDe(Timestamp fecha) {
        return functionCrudRepository.findByDateBefore(fecha).map(functions -> funcionMapper.toFunciones(functions));
    }

    @Override
    public Optional<List<Funcion>> obtenerPorFechaDespuesDe(Timestamp fecha) {
        return functionCrudRepository.findByDateAfter(fecha).map(functions -> funcionMapper.toFunciones(functions));
    }

    @Override
    public Optional<List<Funcion>> obtenerPorRangoFecha(Timestamp fechaI, Timestamp fechaF) {
        return functionCrudRepository.findByDateBetween(fechaI, fechaF).map(functions -> funcionMapper.toFunciones(functions));
    }

    @Override
    public Optional<Funcion> obtenerFuncion(long funcionId) {
        return functionCrudRepository.findById(funcionId).map(function -> funcionMapper.toFuncion(function));
    }

    @Override
    public Funcion save(Funcion funcion) {
        return funcionMapper.toFuncion(functionCrudRepository.save(funcionMapper.toFunction(funcion)));
    }

    @Override
    public void update(Funcion funcion) {
        Function function = functionCrudRepository.findById(funcion.getFuncionId()).orElseThrow(EntityNotFoundException::new);
        funcionMapper.updateFunctionFromFuncion(funcion, function);
        functionCrudRepository.save(function);
    }

    @Override
    public void delete(long funcionId) {
        functionCrudRepository.deleteById(funcionId);
    }
}
