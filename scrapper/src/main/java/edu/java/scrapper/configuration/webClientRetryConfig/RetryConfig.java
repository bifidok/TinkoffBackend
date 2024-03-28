package edu.java.scrapper.configuration.webClientRetryConfig;

import edu.java.scrapper.configuration.webClientRetryConfig.types.ConstantRetry;
import edu.java.scrapper.configuration.webClientRetryConfig.types.CustomRetry;
import edu.java.scrapper.configuration.webClientRetryConfig.types.ExponentialRetry;
import edu.java.scrapper.configuration.webClientRetryConfig.types.LinearRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

@Configuration
@EnableConfigurationProperties(RetryProperties.class)
public class RetryConfig {

    private final RetryProperties retryProperties;

    @Autowired
    public RetryConfig(RetryProperties retryProperties) {
        this.retryProperties = retryProperties;
    }

    @Bean
    @ConditionalOnProperty(prefix = "client", name = "retry", havingValue = "constant")
    public CustomRetry constantRetry() {
        return new ConstantRetry(retryProperties.initialDelay());
    }

    @Bean
    @ConditionalOnProperty(prefix = "client", name = "retry", havingValue = "linear")
    public CustomRetry linearRetry() {
        return new LinearRetry(retryProperties.initialDelay(), retryProperties.increment(), retryProperties.maxDelay());
    }

    @Bean
    @ConditionalOnProperty(prefix = "client", name = "retry", havingValue = "exponential")
    public CustomRetry exponentialRetry() {
        return new ExponentialRetry(retryProperties.initialDelay(), retryProperties.maxDelay());
    }

    @Bean
    public ExchangeFilterFunction exchangeFilterFunction(CustomRetry customRetry) {
        return new CustomExchangeFilterFunction(retryProperties.maxAttempts(), customRetry, retryProperties.codes());
    }
}
