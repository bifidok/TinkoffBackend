package edu.java.scrapper.exceptions;

public class LinkNotCreatedException extends RuntimeException {
    private final static String DESCRIPTION = "Cant create this link";

    public LinkNotCreatedException() {
        super(DESCRIPTION);
    }

    public LinkNotCreatedException(String message) {
        super(message);
    }
}
