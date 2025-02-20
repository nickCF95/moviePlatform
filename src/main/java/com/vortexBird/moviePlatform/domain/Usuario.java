package com.vortexBird.moviePlatform.domain;

import lombok.Data;

import java.util.Set;

@Data
public class Usuario {
    private String alias;
    private String contrasena;
    private String correo;
    private DetallesUsuario detallesUsuario;
    private Set<Rol> roles;
    private Boolean habilitado;
}
