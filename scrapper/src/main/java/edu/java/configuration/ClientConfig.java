package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@ConfigurationProperties
public class ClientConfig {
    @Bean
    public GitHubClient gitHubClient(ApplicationConfig applicationConfig) {
        WebClient webClient = WebClient.builder()
            .baseUrl(applicationConfig.gitHubBaseUrl())
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GitHubClient.class);
    }

    @Bean
    public StackOverflowClient stackOverflowClient(ApplicationConfig applicationConfig) {
        WebClient webClient = WebClient.builder()
            .baseUrl(applicationConfig.stackOverflowBaseUrl())
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(StackOverflowClient.class);
    }
}
