package edu.java.scrapper.configuration;

import edu.java.scrapper.configuration.databaseConfig.enums.AccessType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler,
    @NotEmpty
    String gitHubBaseUrl,
    @NotEmpty
    String stackOverflowBaseUrl,
    @NotEmpty
    String botBaseUrl,
    int linkCheckDelayInHours,
    AccessType databaseAccessType,
    boolean useQueue,
    KafkaConfig kafka
) {
    public record Scheduler(boolean enable, @NotNull Duration interval) {
    }

    public record KafkaConfig(String bootstrapServers, String scrapperTopic) {

    }
}
