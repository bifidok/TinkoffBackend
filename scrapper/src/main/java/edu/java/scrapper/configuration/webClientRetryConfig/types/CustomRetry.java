package edu.java.scrapper.configuration.webClientRetryConfig.types;

import java.time.Duration;

public interface CustomRetry {
    Duration calculateDelay(int attempts);
}
