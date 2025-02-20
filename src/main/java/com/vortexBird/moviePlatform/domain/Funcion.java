package com.vortexBird.moviePlatform.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Funcion {
    private Long funcionId;
    private Timestamp fecha;
    private Pelicula pelicula;
    private SalaCine salaCine;
    private Boolean habilitada;
}
