package edu.java.scrapper.exceptions;

public class LinkNotFoundException extends RuntimeException {
    private final static String DESCRIPTION = "Link not found";

    public LinkNotFoundException() {
        super(DESCRIPTION);
    }

    public LinkNotFoundException(String message) {
        super(message);
    }
}
