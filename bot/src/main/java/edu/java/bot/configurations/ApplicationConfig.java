package edu.java.bot.configurations;

import jakarta.validation.constraints.NotEmpty;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotEmpty
    Map<String, String> commands,
    @NotEmpty
    String scrapperBaseUrl,
    @NotEmpty
    String scrapperTopic
) {
}
