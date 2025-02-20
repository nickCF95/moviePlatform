package com.vortexBird.moviePlatform.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TentativaCompraDTO {
    private long idFuncion;
    private String nickname;
    private long metodoPagoUsuarioId;
    private List<String> posiblesSillas;
}
