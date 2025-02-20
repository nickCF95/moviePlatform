package com.vortexBird.moviePlatform.domain.customExceptions;

public class ExpiredCardException extends RuntimeException {
    public ExpiredCardException(String message) {
        super(message);
    }
}
