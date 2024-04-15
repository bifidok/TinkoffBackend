package edu.java.scrapper.exceptions;

public class ChatNotFoundException extends RuntimeException {
    private final static String DESCRIPTION = "Chat not found";

    public ChatNotFoundException() {
        super(DESCRIPTION);
    }

    public ChatNotFoundException(String message) {
        super(message);
    }
}
