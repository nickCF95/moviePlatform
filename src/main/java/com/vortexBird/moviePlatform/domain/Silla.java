package com.vortexBird.moviePlatform.domain;

import lombok.Data;

@Data
public class Silla {
    private Integer sillaId;
    private String codigoSilla;
    private SalaCine salaCine;
}
