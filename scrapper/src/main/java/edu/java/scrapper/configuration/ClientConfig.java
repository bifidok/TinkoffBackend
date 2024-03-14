package edu.java.scrapper.configuration;

import edu.java.scrapper.clients.BotClient;
import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.exceptions.GitHubClientException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Configuration
public class ClientConfig {
    @Bean
    public GitHubClient gitHubClient(ApplicationConfig applicationConfig) {
        WebClient webClient = WebClient.builder()
            .defaultStatusHandler(HttpStatusCode::isError,clientResponse ->
                Mono.just(new GitHubClientException())
            )
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

    @Bean
    public BotClient botClient(ApplicationConfig applicationConfig) {
        WebClient webClient = WebClient.builder()
            .baseUrl(applicationConfig.botBaseUrl())
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(BotClient.class);
    }
}
