package edu.java.scrapper.controllers;

import edu.java.scrapper.dto.AddLinkRequest;
import edu.java.scrapper.dto.LinkResponse;
import edu.java.scrapper.dto.ListLinksResponse;
import edu.java.scrapper.dto.RemoveLinkRequest;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.services.LinkService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/links")
@RestController
public class LinkController {
    private final LinkService linkService;

    @Autowired
    public LinkController(@Qualifier("jdbcLinkService") LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public ListLinksResponse getAll(@RequestHeader long tgChatId) {
        List<Link> links = linkService.findAll(tgChatId);
        List<LinkResponse> listLinksResponse = links.stream()
            .map(link -> new LinkResponse(link.getId(), link.getUrl()))
            .toList();
        return new ListLinksResponse(listLinksResponse, listLinksResponse.size());
    }

    @PostMapping
    public HttpStatus addLink(@RequestHeader long tgChatId, @RequestBody @Valid AddLinkRequest addLinkRequest) {
        linkService.add(tgChatId, addLinkRequest.getLink());
        return HttpStatus.OK;
    }

    @DeleteMapping
    public HttpStatus removeLink(
        @RequestHeader long tgChatId,
        @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) {
        linkService.remove(tgChatId, removeLinkRequest.getLink());
        return HttpStatus.OK;
    }
}
