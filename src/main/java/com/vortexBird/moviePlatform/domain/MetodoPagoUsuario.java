package com.vortexBird.moviePlatform.domain;

import lombok.Data;

@Data
public class MetodoPagoUsuario {
    private Long metodoPagoUsuarioId;

    private String numeroTarjeta;

    private String fechaExpiracion;

    private String cvc;

    private Usuario usuario;

    private Boolean habilitado;
}
