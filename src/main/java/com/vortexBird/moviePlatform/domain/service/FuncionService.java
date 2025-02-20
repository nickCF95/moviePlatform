package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.Funcion;
import com.vortexBird.moviePlatform.domain.Pelicula;
import com.vortexBird.moviePlatform.domain.SalaCine;
import com.vortexBird.moviePlatform.domain.repository.FuncionRepository;
import com.vortexBird.moviePlatform.domain.repository.PeliculaRepository;
import com.vortexBird.moviePlatform.domain.repository.SalaCineRepository;
import com.vortexBird.moviePlatform.domain.repository.TiqueteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionService {
    @Autowired
    private FuncionRepository funcionRepository;

    @Autowired
    private SalaCineRepository salaCineRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    public Optional<Funcion> obtenerfuncion(long funcionId) {
        return funcionRepository.obtenerFuncion(funcionId);
    }

    public List<Funcion> obtenerTodas(boolean justEnabled){
        List<Funcion> emptyList = new ArrayList<>();
        return  funcionRepository.obtenerTodas(justEnabled).orElse(emptyList);
    }

    public Optional<List<Funcion>> obtenerPorPelicula(long peliculaId, boolean justEnabled){
        if(justEnabled){
            return funcionRepository.obtenerHabilitadaPorPelicula(peliculaId);
        } else {
            return funcionRepository.obtenerPorPelicula(peliculaId);
        }
    }

    public Optional<List<Funcion>> obtenerPorSalaCine(int salaId, boolean justEnabled){
        if (justEnabled){
            return funcionRepository.obtenerHabilitadaPorSalaCine(salaId);
        } else {
            return funcionRepository.obtenerPorSalaCine(salaId);
        }
    }

    public Funcion save(Funcion funcion) {
        SalaCine salaCine = salaCineRepository
                .obtenerSalaCine(funcion.getSalaCine().getSalaId())
                .orElseThrow(() -> new EntityNotFoundException("Cinema room not found"));

        if (!salaCine.getHabilitada()) {
            throw new IllegalStateException("The cinema room is not enabled");
        }

        Pelicula pelicula = peliculaRepository
                .obtenerPelicula(funcion.getPelicula().getPeliculaId())
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        if (!pelicula.getDisponible()) {
            throw new IllegalStateException("The movie is disabled");
        }
        funcion.setHabilitada(true);
        return funcionRepository.save(funcion);
    }

    public boolean update(Funcion funcion){
        try {
            funcionRepository.update(funcion);
            return true;
        } catch (NoResultException e){
            return false;
        }
    }

    public boolean inhabilitar(long funcionId){
        return obtenerfuncion(funcionId).map(funcion -> {
            funcion.setHabilitada(false);
            funcionRepository.update(funcion);
            return true;
        }).orElse(false);
    }

    public boolean habilitar(int funcionId){
        return obtenerfuncion(funcionId).map(funcion -> {
            funcion.setHabilitada(true);
            funcionRepository.update(funcion);
            return true;
        }).orElse(false);
    }

    public boolean delete(int funcionId) {
        return obtenerfuncion(funcionId).map(funcion -> {
            funcionRepository.delete(funcionId);
            return true;
        }).orElse(false);
    }
}
