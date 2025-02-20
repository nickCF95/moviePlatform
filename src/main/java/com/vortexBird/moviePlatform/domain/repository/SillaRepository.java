package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.Silla;

import java.util.List;
import java.util.Optional;

public interface SillaRepository {
    Optional<List<Silla>> obtenerTodas();
    Optional<List<Silla>> obtenerPorSalaCine(int salaId);
    Optional<Silla> obtenerSilla(int sillaId);
    Optional<Silla> obtenerPorCodigoSillaYSalaCine(String codigoSilla, int salaId);
    Silla save(Silla silla);
    void delete(int sillaId);
}
