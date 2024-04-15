package edu.java.scrapper.exceptions;

public class LinkDoubleCreationException extends RuntimeException {
    private final static String DESCRIPTION = "This link already in track list if this chat";

    public LinkDoubleCreationException() {
        super(DESCRIPTION);
    }

    public LinkDoubleCreationException(String message) {
        super(message);
    }
}
