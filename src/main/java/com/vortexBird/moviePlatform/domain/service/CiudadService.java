package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.repository.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadService {
    @Autowired
    private CiudadRepository ciudadRepository;
}
