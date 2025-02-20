package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.repository.SillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SillaService {
    @Autowired
    private SillaRepository sillaRepository;
}
