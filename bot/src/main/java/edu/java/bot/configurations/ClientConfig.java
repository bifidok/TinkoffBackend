package edu.java.bot.configurations;

import edu.java.bot.clients.ScrapperClient;
import edu.java.bot.exceptions.ScrapperClientException;
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
    public ScrapperClient scrapperClient(ApplicationConfig applicationConfig) {
        WebClient webClient = WebClient.builder()
            .defaultStatusHandler(HttpStatusCode::isError, clientResponse ->
                Mono.just(new ScrapperClientException(clientResponse.statusCode().toString()))
            )
            .baseUrl(applicationConfig.scrapperBaseUrl())
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ScrapperClient.class);
    }
}
