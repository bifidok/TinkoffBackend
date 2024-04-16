package edu.java.scrapper.exceptions;

public class ChatNotCreatedException extends RuntimeException {
    private final static String DESCRIPTION = "Some problems with chat creation";

    public ChatNotCreatedException() {
        super(DESCRIPTION);
    }

    public ChatNotCreatedException(String message) {
        super(message);
    }
}
