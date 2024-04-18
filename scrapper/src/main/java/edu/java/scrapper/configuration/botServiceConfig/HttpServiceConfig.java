package edu.java.scrapper.configuration.botServiceConfig;

import edu.java.scrapper.bot.BotService;
import edu.java.scrapper.bot.HttpBotService;
import edu.java.scrapper.clients.BotClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpServiceConfig {
    @Bean
    public BotService botService(BotClient botClient) {
        return new HttpBotService(botClient);
    }
}
