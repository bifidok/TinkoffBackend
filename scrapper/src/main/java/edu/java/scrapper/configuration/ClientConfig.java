package edu.java.scrapper.configuration;

import edu.java.scrapper.clients.BotClient;
import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.exceptions.GitHubClientException;
import edu.java.scrapper.exceptions.StackOverflowClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class ClientConfig {
    private final ExchangeFilterFunction exchangeFilterFunction;
    private final ApplicationConfig applicationConfig;

    @Autowired
    public ClientConfig(ExchangeFilterFunction exchangeFilterFunction, ApplicationConfig applicationConfig) {
        this.exchangeFilterFunction = exchangeFilterFunction;
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public GitHubClient gitHubClient() {
        WebClient webClient = WebClient.builder()
            .defaultStatusHandler(HttpStatusCode::isError, clientResponse ->
                Mono.just(new GitHubClientException())
            )
            .baseUrl(applicationConfig.gitHubBaseUrl())
            .filter(exchangeFilterFunction)
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GitHubClient.class);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        WebClient webClient = WebClient.builder()
            .defaultStatusHandler(HttpStatusCode::isError, clientResponse ->
                Mono.just(new StackOverflowClientException())
            )
            .baseUrl(applicationConfig.stackOverflowBaseUrl())
            .filter(exchangeFilterFunction)
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(StackOverflowClient.class);
    }

    @Bean
    public BotClient botClient() {
        WebClient webClient = WebClient.builder()
            .baseUrl(applicationConfig.botBaseUrl())
            .filter(exchangeFilterFunction)
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(BotClient.class);
    }
}
