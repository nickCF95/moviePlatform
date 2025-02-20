package com.vortexBird.moviePlatform.domain;

import lombok.Data;

@Data
public class SalaCine {
    private Integer salaId;

    private Integer capacidad;

    private Short numeroSala;

    private Ubicacion ubicacion;

    private Boolean habilitada;

    private Integer precioBase;
}
