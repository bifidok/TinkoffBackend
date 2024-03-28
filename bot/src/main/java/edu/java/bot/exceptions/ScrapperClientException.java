package edu.java.bot.exceptions;

public class ScrapperClientException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Something went wrong";

    public ScrapperClientException() {
        super(DEFAULT_MESSAGE);
    }

    public ScrapperClientException(String message) {
        super(message);
    }
}
