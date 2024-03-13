package edu.java.bot.clients;

import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.dto.ListLinksResponse;
import edu.java.bot.dto.RemoveLinkRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {
    @PostExchange("/tg-chat/{id}")
    HttpStatus register(@PathVariable("id") long tgChatId);

    @DeleteExchange("/tg-chat/{id}")
    HttpStatus delete(@PathVariable("id") long tgChatId);

    @GetExchange("/links")
    ListLinksResponse getAll(@RequestHeader long tgChatId);

    @PostExchange("/links")
    HttpStatus addLink(@RequestHeader long tgChatId, @RequestBody AddLinkRequest addLinkRequest);

    @DeleteExchange("/links")
    HttpStatus removeLink(@RequestHeader long tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest);
}
