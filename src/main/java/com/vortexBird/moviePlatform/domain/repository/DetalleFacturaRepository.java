package com.vortexBird.moviePlatform.domain.repository;

import com.vortexBird.moviePlatform.domain.DetalleFactura;

import java.util.List;
import java.util.Optional;

public interface DetalleFacturaRepository {
    Optional<List<DetalleFactura>> obtenerTodos();
    Optional<List<DetalleFactura>> obtenerPorFactura(long facturaId);
    Optional<List<DetalleFactura>> obtenerPorTiquete(long tiqueteId);
    Optional<DetalleFactura> obtenerDetalleFactura(long detalleFacturaId);
    DetalleFactura save(DetalleFactura detalleFactura);
    List<DetalleFactura> saveAll(List<DetalleFactura> detallesFactura);
    void delete(long detalleFacturaId);

}
