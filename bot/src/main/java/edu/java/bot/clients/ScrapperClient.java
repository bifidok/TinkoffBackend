package edu.java.bot.clients;

import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.dto.ListLinksResponse;
import edu.java.bot.dto.RemoveLinkRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {
    @PostExchange("/tg-chat/{id}")
    HttpStatus register(@PathVariable("id") int tgChatId);

    @DeleteExchange("/tg-chat/{id}")
    HttpStatus delete(@PathVariable("id") int tgChatId);

    @GetExchange("/links/{id}")
    ListLinksResponse getAll(@PathVariable("id") int tgChatId);

    @PostExchange("/links/{id}")
    HttpStatus addLink(@PathVariable("id") int tgChatId, @RequestBody AddLinkRequest addLinkRequest);

    @DeleteExchange("/links/{id}")
    HttpStatus removeLink(@PathVariable("id") int tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest);
}
