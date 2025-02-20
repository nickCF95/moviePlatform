package com.vortexBird.moviePlatform.domain.customExceptions;

public class TicketAlreadySoldException extends RuntimeException {

    public TicketAlreadySoldException(String message) {
        super(message);
    }

    public TicketAlreadySoldException(String message, Throwable cause) {
        super(message, cause);
    }
}