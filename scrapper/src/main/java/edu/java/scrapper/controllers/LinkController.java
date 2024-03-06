package edu.java.scrapper.controllers;

import edu.java.scrapper.dto.AddLinkRequest;
import edu.java.scrapper.dto.LinkResponse;
import edu.java.scrapper.dto.ListLinksResponse;
import edu.java.scrapper.dto.RemoveLinkRequest;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Chat;
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

    @GetMapping("/{id}")
    public ListLinksResponse getAll(@PathVariable("id") int tgChatId) {
        Chat chat = chatService.findById(tgChatId);
        if (chat == null) {
            throw new ChatNotFoundException();
        }
        List<Link> links = chat.getLinks();
        List<LinkResponse> listLinksResponse = links.stream()
            .map(link -> new LinkResponse(link.getId(), link.getUrl()))
            .toList();
        return new ListLinksResponse(listLinksResponse, listLinksResponse.size());
    }

    @PostMapping("/{id}")
    public HttpStatus addLink(@PathVariable("id") int tgChatId, @RequestBody AddLinkRequest addLinkRequest) {
        Chat chat = chatService.findById(tgChatId);
        if (chat == null) {
            throw new ChatNotFoundException();
        }
        Link link = new Link(addLinkRequest.getLink());
        linkService.add(link);
        chat.getLinks().add(link);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}")
    public HttpStatus removeLink(@PathVariable("id") int tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest) {
        Chat chat = chatService.findById(tgChatId);
        if (chat == null) {
            throw new ChatNotFoundException();
        }
        Link link = new Link(removeLinkRequest.getLink());
        linkService.remove(link);
        chat.getLinks().remove(link);
        return HttpStatus.OK;
    }
}
