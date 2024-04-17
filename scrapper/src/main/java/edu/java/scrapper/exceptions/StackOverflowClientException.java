package edu.java.scrapper.exceptions;

public class StackOverflowClientException extends RuntimeException {
    private final static String DESCRIPTION = "Something went wrong in StackOverflow Client";

    public StackOverflowClientException() {
        super(DESCRIPTION);
    }

    public StackOverflowClientException(String message) {
        super(message);
    }
}
