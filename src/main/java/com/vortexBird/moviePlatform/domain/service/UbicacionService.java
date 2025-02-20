package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UbicacionService {
    @Autowired
    private UbicacionRepository ubicacionRepository;
}
