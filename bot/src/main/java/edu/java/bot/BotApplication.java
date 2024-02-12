package edu.java.bot;

import edu.java.bot.configurations.ApplicationConfig;
import edu.java.bot.services.TGBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    @Autowired
    private static TGBot bot;

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }
}
