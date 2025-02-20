package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.repository.DetallesUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallesUsuarioService {
    @Autowired
    private DetallesUsuarioRepository detallesUsuarioRepository;
}
