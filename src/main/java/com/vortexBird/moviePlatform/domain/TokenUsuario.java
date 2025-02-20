package com.vortexBird.moviePlatform.domain;

import com.vortexBird.moviePlatform.persistence.enums.TokenType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
public class TokenUsuario {
    private Long id;

    private String token;

    private TokenType tipo;

    private Date fechaExpiracion;

    private Usuario usuario;

    public TokenUsuario(String token, TokenType tipo, Usuario usuario) {
        this.token = token;
        this.tipo = tipo;
        this.usuario = usuario;
        this.fechaExpiracion = calcularFechaExpiracion(tipo);
    }

    public TokenUsuario(String token, TokenType tipo, Usuario usuario, Date fechaExpiracion) {
        this.token = token;
        this.tipo = tipo;
        this.usuario = usuario;
        this.fechaExpiracion = fechaExpiracion;
    }

    private Date calcularFechaExpiracion(TokenType type) {
        Calendar cal = Calendar.getInstance();
        switch (type) {
            case CONFIRMATION:
                cal.add(Calendar.MINUTE, 24 * 60);
                break;
            case RECOVERY:
                cal.add(Calendar.MINUTE, 60);
                break;
            default:
                cal.add(Calendar.MINUTE, 60);
                break;
        }
        return cal.getTime();
    }
}
