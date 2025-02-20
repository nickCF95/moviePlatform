package com.vortexBird.moviePlatform.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Factura {
    private Long facturaId;

    private Timestamp fecha;

    private Integer numeroTiquetes;

    private Double precio;

    private MetodoPagoUsuario metodoPagoUsuario;

    private List<DetalleFactura> detallesFactura;
}
