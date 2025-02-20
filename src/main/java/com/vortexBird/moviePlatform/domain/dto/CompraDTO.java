package com.vortexBird.moviePlatform.domain.dto;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Data
@ToString
public class CompraDTO {
    private String codigoOrden;
    private Timestamp fechaCompra;
    private String nombrePelicula;
    private short numeroSalaCine;
    private Timestamp fechaFuncion;
    private List<String> sillas;
}
