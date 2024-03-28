package edu.java.bot.configurations.webClientRetryConfig.types;

import java.time.Duration;

public record ExponentialRetry(Duration initialDelay, Duration maxDelay) implements CustomRetry {
    @Override
    public Duration calculateDelay(int attempts) {
        Duration delay = initialDelay.multipliedBy((long) Math.pow(2, attempts - 1));
        return delay.compareTo(maxDelay) < 0 ? delay : maxDelay;
    }
}
