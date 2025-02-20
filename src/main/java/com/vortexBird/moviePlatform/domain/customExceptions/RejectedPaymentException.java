package com.vortexBird.moviePlatform.domain.customExceptions;

public class RejectedPaymentException extends RuntimeException {
    public RejectedPaymentException(String message) {
        super(message);
    }
}
