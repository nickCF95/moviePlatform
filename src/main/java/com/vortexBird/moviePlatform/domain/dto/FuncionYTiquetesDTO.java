package com.vortexBird.moviePlatform.domain.dto;

import com.vortexBird.moviePlatform.domain.Funcion;
import com.vortexBird.moviePlatform.domain.Tiquete;
import lombok.Data;

import java.util.List;

@Data
public class FuncionYTiquetesDTO {
    Funcion funcion;
    List<Tiquete> tiquetes;
}
