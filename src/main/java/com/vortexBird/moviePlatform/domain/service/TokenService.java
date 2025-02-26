package com.vortexBird.moviePlatform.domain.service;


import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class TokenService {

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
