package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.Factura;
import com.vortexBird.moviePlatform.domain.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    public List<Factura> obtenerTodas() {
        List<Factura> emptyList = new ArrayList<>();
        return facturaRepository.obtenerTodas().orElse(emptyList);
    }

    public Optional<Factura> obtenerFactura(int FacturaId) {
        return facturaRepository.obtenerFactura(FacturaId);
    }

    public Factura save(Factura Factura) {
        return facturaRepository.save(Factura);
    }

    public boolean delete(int FacturaId) {
        return obtenerFactura(FacturaId).map(Factura -> {
            facturaRepository.delete(Factura.getFacturaId());
            return true;
        }).orElse(false);
    }
}
