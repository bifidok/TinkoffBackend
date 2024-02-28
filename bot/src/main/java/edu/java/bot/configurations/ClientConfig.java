package edu.java.bot.configurations;

import edu.java.bot.clients.ScrapperClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@ConfigurationProperties
public class ClientConfig {
    @Bean
    public ScrapperClient scrapperClient(ApplicationConfig applicationConfig) {
        WebClient webClient = WebClient.builder()
            .baseUrl(applicationConfig.scrapperBaseUrl())
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ScrapperClient.class);
    }
}
