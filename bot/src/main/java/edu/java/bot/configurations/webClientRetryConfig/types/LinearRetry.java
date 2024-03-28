package edu.java.bot.configurations.webClientRetryConfig.types;

import java.time.Duration;

public record LinearRetry(Duration initialDelay, Duration increment, Duration maxDelay) implements CustomRetry {
    @Override
    public Duration calculateDelay(int attempts) {
        Duration delay = initialDelay.plus(increment.multipliedBy(attempts));
        return delay.compareTo(maxDelay) < 0 ? delay : maxDelay;
    }
}
