package com.vortexBird.moviePlatform.domain;

import lombok.Data;

@Data
public class Tiquete {
    private Long tiqueteId;

    private Funcion funcion;

    private Silla silla;

    private Boolean disponible;

    private Double precioIndividual;
}
