package com.vortexBird.moviePlatform.domain.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SillaDTO {
    private String codigoSilla;
    private long tiqueteId;
    private double precioIndividual;
    private boolean disponible;
}
