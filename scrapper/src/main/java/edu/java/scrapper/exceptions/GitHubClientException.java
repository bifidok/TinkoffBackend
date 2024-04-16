package edu.java.scrapper.exceptions;

public class GitHubClientException extends RuntimeException {
    private final static String DESCRIPTION = "Something went wrong in Git Hub Client";

    public GitHubClientException() {
        super(DESCRIPTION);
    }

    public GitHubClientException(String message) {
        super(message);
    }
}
