package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.SalaCine;

import java.util.List;
import java.util.Optional;

public interface SalaCineRepository {
    Optional<List<SalaCine>> obtenerTodas();
    Optional<List<SalaCine>> obtenerPorNumeroSala(short numeroSala);
    Optional<List<SalaCine>> obtenerPorUbicacion(int ubicacionId);
    Optional<List<SalaCine>> ObtenerPorCapacidad(int capacidad);
    Optional<List<SalaCine>> ObtenerPorCapacidadMayorA(int capacidad);
    Optional<List<SalaCine>> ObtenerPorCapacidadMenorA(int capacidad);
    Optional<List<SalaCine>> ObtenerPorRangoCapacidad(int capacidadI, int capacidadF);
    Optional<SalaCine> obtenerSalaCine(int salaId);
    Optional<SalaCine> obtenerPorNumeroSalaYUbicacion(short numeroSala, int ubicacionId);
    SalaCine save(SalaCine salaCine);
    void delete(int salaId);

}
