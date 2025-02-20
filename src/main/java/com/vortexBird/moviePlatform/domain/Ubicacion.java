package com.vortexBird.moviePlatform.domain;

import lombok.Data;

@Data
public class Ubicacion {
    private Integer ubicacionId;

    private String nombre;

    private String direccion;

    private Ciudad ciudad;
}
