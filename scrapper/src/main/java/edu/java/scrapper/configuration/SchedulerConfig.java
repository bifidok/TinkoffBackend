package edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "app.scheduler", name = "enable", havingValue = "true")
public class SchedulerConfig {
    private final ApplicationConfig.Scheduler scheduler;

    @Autowired
    public SchedulerConfig(ApplicationConfig applicationConfig) {
        scheduler = applicationConfig.scheduler();
    }

    @Bean
    public String schedulerDelay() {
        return String.valueOf(scheduler.interval().toMillis());
    }
}
