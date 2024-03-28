package edu.java.scrapper.configuration.webClientRetryConfig;

import edu.java.scrapper.configuration.webClientRetryConfig.types.CustomRetry;
import java.time.Duration;
import java.util.Set;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

public class CustomExchangeFilterFunction implements ExchangeFilterFunction {
    private final CustomRetry customRetry;
    private final Set<Integer> codes;
    private int maxAttempts;

    public CustomExchangeFilterFunction(int maxAttempts, CustomRetry customRetry, Set<Integer> codes) {
        this.maxAttempts = maxAttempts;
        this.customRetry = customRetry;
        this.codes = codes;
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        return retry(request, next, 1);
    }

    private Mono<ClientResponse> retry(ClientRequest request, ExchangeFunction next, int attempt) {
        return next.exchange(request)
            .flatMap(response -> {
                if (codes.contains(response.statusCode().value()) && attempt <= maxAttempts) {
                    Duration delay = customRetry.calculateDelay(attempt);
                    return Mono.delay(delay)
                        .then(Mono.defer(() -> retry(request, next, attempt + 1)));
                }
                return Mono.just(response);
            });
    }
}
