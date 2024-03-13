package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.clients.BotClient;
import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.clients.responses.RepositoryResponse;
import edu.java.scrapper.dto.LinkUpdateRequest;
import edu.java.scrapper.linkParser.links.GitHubLink;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JdbcGitHubLinkUpdater {
    private final LinkService linkService;
    private final ChatService chatService;
    private final GitHubClient gitHubClient;
    private final BotClient botClient;

    @Autowired
    public JdbcGitHubLinkUpdater(
        LinkService linkService,
        ChatService chatService, GitHubClient gitHubClient,
        BotClient botClient
    ) {
        this.linkService = linkService;
        this.chatService = chatService;
        this.gitHubClient = gitHubClient;
        this.botClient = botClient;
    }

    public void update(GitHubLink gitHubLink, Link link) {
        RepositoryResponse response = gitHubClient.get(gitHubLink.owner(), gitHubLink.repo());
        checkByLastActivity(link, response);
        updateLinkDates(link, response.lastActivity());
    }

    private void checkByLastActivity(Link link, RepositoryResponse response) {
        if (link.getLastActivity().isBefore(response.lastActivity())) {
            List<Chat> chats = chatService.findAll(link.getUrl());
            List<Long> chatsIds = chats.stream().map(Chat::getId).toList();
            LinkUpdateRequest request =
                new LinkUpdateRequest(link.getId(), link.getUrl(), "Check new updates", chatsIds);
            botClient.checkUpdate(request);
        }
    }

    private void updateLinkDates(Link link, OffsetDateTime lastActivity) {
        link.setLastCheckTime(OffsetDateTime.now());
        link.setLastActivity(lastActivity);
        linkService.update(link);
    }
}
