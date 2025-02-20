package com.vortexBird.moviePlatform.domain.customExceptions;

public class WrongFormatException extends RuntimeException {
    public WrongFormatException(String message) {
        super(message);
    }
}
