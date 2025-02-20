package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.Tiquete;

import java.util.List;
import java.util.Optional;

public interface TiqueteRepository {
    Optional<List<Tiquete>> obtenerTodos();
    Optional<List<Tiquete>> obtenerPorSilla(int sillaId);
    Optional<List<Tiquete>> obtenerPorFuncion(long funcionId);
    Optional<List<Tiquete>> obtenerPorDisponibilidad(boolean disponible);
    Optional<List<Tiquete>> obtenerDisponiblesPorFuncion(long funcionId);
    Optional<List<Tiquete>> obtenerPorCodigoSillasYFuncionConBloqueo(List<String> sillas, long funcionId);
    Optional<Tiquete> obtenerTiquete(long tiqueteId);
    List<Tiquete> saveAll(List<Tiquete> tiquetes);
    Tiquete save(Tiquete tiquete);
    void update(Tiquete tiquete);
    void delete(long tiqueteId);
}
