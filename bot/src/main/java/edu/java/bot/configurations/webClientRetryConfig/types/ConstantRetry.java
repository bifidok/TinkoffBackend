package edu.java.bot.configurations.webClientRetryConfig.types;

import java.time.Duration;

public record ConstantRetry(Duration initialDelay) implements CustomRetry {
    @Override
    public Duration calculateDelay(int attempts) {
        return initialDelay;
    }
}
