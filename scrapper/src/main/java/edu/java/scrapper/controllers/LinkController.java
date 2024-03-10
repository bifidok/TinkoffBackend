package edu.java.scrapper.controllers;

import edu.java.scrapper.dto.AddLinkRequest;
import edu.java.scrapper.dto.LinkResponse;
import edu.java.scrapper.dto.ListLinksResponse;
import edu.java.scrapper.dto.RemoveLinkRequest;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.TelegramChat;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/links")
@RestController
public class LinkController {
    private final LinkService linkService;
    private final ChatService chatService;

    @Autowired
    public LinkController(LinkService linkService, ChatService chatService) {
        this.linkService = linkService;
        this.chatService = chatService;
    }

    @GetMapping
    public ListLinksResponse getAll(@RequestHeader int tgChatId) {
        TelegramChat telegramChat = chatService.findById(tgChatId);
        if (telegramChat == null) {
            throw new ChatNotFoundException();
        }
        List<Link> links = telegramChat.getLinks();
        List<LinkResponse> listLinksResponse = links.stream()
            .map(link -> new LinkResponse(link.getId(), link.getLink()))
            .toList();
        return new ListLinksResponse(listLinksResponse, listLinksResponse.size());
    }

    @PostMapping
    public HttpStatus addLink(@RequestHeader int tgChatId, @RequestBody AddLinkRequest addLinkRequest) {
        TelegramChat telegramChat = chatService.findById(tgChatId);
        if (telegramChat == null) {
            throw new ChatNotFoundException();
        }
        Link link = new Link(addLinkRequest.getLink());
        linkService.add(link);
        telegramChat.getLinks().add(link);
        return HttpStatus.OK;
    }

    @DeleteMapping
    public HttpStatus removeLink(@RequestHeader int tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest) {
        TelegramChat telegramChat = chatService.findById(tgChatId);
        if (telegramChat == null) {
            throw new ChatNotFoundException();
        }
        Link link = new Link(removeLinkRequest.getLink());
        linkService.remove(link);
        telegramChat.getLinks().remove(link);
        return HttpStatus.OK;
    }
}
