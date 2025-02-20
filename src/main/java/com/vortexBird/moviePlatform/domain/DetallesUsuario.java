package com.vortexBird.moviePlatform.domain;

import lombok.Data;

import java.util.Date;

@Data
public class DetallesUsuario {
    private String alias;
    private String nombreCompleto;
    private Date fechaNacimiento;
    private String direccion;
    private String numeroTelefono;
}
