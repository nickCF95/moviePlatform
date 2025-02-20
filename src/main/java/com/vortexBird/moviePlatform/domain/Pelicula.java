package com.vortexBird.moviePlatform.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
public class Pelicula {
    private Long peliculaId;

    private String titulo;

    private String director;

    private Set<Categoria> categorias;

    private Timestamp fechaLanzamiento;

    private Boolean disponible;

    private String nombreArchivoImagen;
}
