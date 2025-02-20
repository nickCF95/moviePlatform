package com.vortexBird.moviePlatform.domain;

import lombok.Data;

@Data
public class DetalleFactura {
    private Long detalleFacturaId;

    private Tiquete tiquete;

    private Factura factura;
}
