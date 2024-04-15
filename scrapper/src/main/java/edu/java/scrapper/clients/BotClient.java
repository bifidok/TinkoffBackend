package edu.java.scrapper.clients;

import edu.java.scrapper.dto.LinkUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface BotClient {
    @PostExchange("/updates")
    HttpStatus checkUpdate(@RequestBody LinkUpdateRequest linkUpdate);
}
