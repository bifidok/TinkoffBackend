package edu.java.scrapper.exceptions;

public class LinkNotCorrectException extends RuntimeException {
    private final static String DESCRIPTION = "Not correct link";

    public LinkNotCorrectException() {
        super(DESCRIPTION);
    }

    public LinkNotCorrectException(String message) {
        super(message);
    }
}
