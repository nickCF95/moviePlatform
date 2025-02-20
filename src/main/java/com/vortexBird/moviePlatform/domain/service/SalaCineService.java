package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.repository.SalaCineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaCineService {
    @Autowired
    private SalaCineRepository salaCineRepository;
}
