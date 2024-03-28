package edu.java.bot.configurations.webClientRetryConfig.types;

import java.time.Duration;

public interface CustomRetry {
    Duration calculateDelay(int attempts);
}
