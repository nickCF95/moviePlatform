package com.vortexBird.moviePlatform.domain.customExceptions;

public class IncompleteTicketFetchException extends RuntimeException {

    public IncompleteTicketFetchException(String message) {
        super(message);
    }

    public IncompleteTicketFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}