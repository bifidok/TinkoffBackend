package edu.java.scrapper.configuration.webClientRetryConfig;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "client", ignoreUnknownFields = false)
@Validated
public record RetryProperties(@NotNull
                              Duration initialDelay,
                              @NotNull
                              Duration maxDelay,
                              @NotNull
                              Duration increment,
                              @NotNull
                              int maxAttempts,
                              @NotNull
                              String retry,
                              Set<Integer> codes) {
}
