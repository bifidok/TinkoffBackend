package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.clients.BotClient;
import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.clients.responses.ApiBaseResponse;
import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.dto.LinkUpdateRequest;
import edu.java.scrapper.linkParser.GitHubLinkParser;
import edu.java.scrapper.linkParser.LinkParser;
import edu.java.scrapper.linkParser.StackOverFlowLinkParser;
import edu.java.scrapper.linkParser.links.GitHubLink;
import edu.java.scrapper.linkParser.links.StackOverflowLink;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.services.LinkUpdater;
import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JdbcLinkUpdater implements LinkUpdater {
    private final LinkService linkService;
    private final ChatService chatService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;
    private LinkParser linkParser;
    private int linkInspectionDelayInHours;

    @Autowired
    public JdbcLinkUpdater(
        ApplicationConfig applicationConfig, LinkService linkService,
        ChatService chatService, GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        BotClient botClient
    ) {
        linkInspectionDelayInHours = applicationConfig.linkCheckDelayInHours();
        this.linkService = linkService;
        this.chatService = chatService;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.botClient = botClient;
        linkParser = LinkParser.link(
            new GitHubLinkParser(),
            new StackOverFlowLinkParser()
        );
    }

    @Override
    public void update() {
        OffsetDateTime dateAfterWhichInspection = getDateTimeMinusDelay();
        List<Link> links = linkService.findByCheckDateMoreThan(dateAfterWhichInspection);
        for (Link link : links) {
            ApiBaseResponse response = null;
            response = switch (linkParser.check(link.getUrl())) {
                case GitHubLink gitHub -> gitHubClient.get(gitHub.owner(), gitHub.repo());
                case StackOverflowLink sof -> stackOverflowClient.get(sof.questionId());
                default -> null;
            };
            if (response != null) {
                sendRequestToUpdate(response, link);
            } else {
                log.warn("Not supported link in database");
            }
        }
    }

    private void sendRequestToUpdate(ApiBaseResponse apiBaseResponse, Link link) {
        if (link.getLastActivity().isBefore(apiBaseResponse.getLastActivity())) {
            List<Chat> chats = chatService.findAll(link.getUrl());
            List<Long> chatsIds = chats.stream().map(Chat::getId).toList();
            LinkUpdateRequest request =
                new LinkUpdateRequest(link.getId(), link.getUrl(), "Check new updates", chatsIds);
            botClient.checkUpdate(request);
        }
        updateLinkDates(link, apiBaseResponse.getLastActivity());
    }

    private OffsetDateTime getDateTimeMinusDelay() {
        return OffsetDateTime.now().minusHours(linkInspectionDelayInHours);
    }

    private void updateLinkDates(Link link, OffsetDateTime lastActivity) {
        link.setLastCheckTime(OffsetDateTime.now());
        link.setLastActivity(lastActivity);
        linkService.update(link);
    }
}
