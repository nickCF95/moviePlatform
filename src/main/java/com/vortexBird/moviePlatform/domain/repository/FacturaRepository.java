package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.Factura;
import com.vortexBird.moviePlatform.domain.Factura;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface FacturaRepository {
    Optional<List<Factura>> obtenerTodas();
    Optional<List<Factura>> obtenerPorMetodoPagoUsuario(long metodoPagoUsuarioId);
    Optional<List<Factura>> obtenerPorFecha(Timestamp fecha);
    Optional<List<Factura>> obtenerPorFechaAntesDe(Timestamp fecha);
    Optional<List<Factura>> obtenerPorFechaDespuesDe(Timestamp fecha);
    Optional<List<Factura>> obtenerPorRangoDeFechas(Timestamp fechaI, Timestamp fechaF);
    Optional<List<Factura>> obtenerPorNumeroTiquetes(int noTiquetes);
    Optional<List<Factura>> obtenerPorNumeroTiquetesMayorA(int noTiquetes);
    Optional<List<Factura>> obtenerPorNumeroTiquetesMenorA(int noTiquetes);
    Optional<List<Factura>> obtenerPorRangoDeNumeroTiquetes(int noTiquetesI, int noTiquetesF);
    Optional<Factura> obtenerFactura(long facturaId);
    Factura save(Factura factura);
    void delete(long facturaId);

}
