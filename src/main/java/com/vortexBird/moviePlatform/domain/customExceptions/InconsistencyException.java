package com.vortexBird.moviePlatform.domain.customExceptions;

public class InconsistencyException extends RuntimeException {
    public InconsistencyException(String message) {
        super(message);
    }
}
