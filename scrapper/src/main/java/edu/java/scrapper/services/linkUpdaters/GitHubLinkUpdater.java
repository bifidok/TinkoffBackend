package edu.java.scrapper.services.linkUpdaters;

import edu.java.scrapper.clients.BotClient;
import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.clients.responses.RepositoryCommitsResponse;
import edu.java.scrapper.clients.responses.RepositoryResponse;
import edu.java.scrapper.dto.LinkUpdateRequest;
import edu.java.scrapper.exceptions.GitHubClientException;
import edu.java.scrapper.linkParser.links.GitHubLink;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.GitHubRepositoryService;
import edu.java.scrapper.services.LinkService;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GitHubLinkUpdater {
    private final LinkService linkService;
    private final GitHubRepositoryService gitHubRepositoryService;
    private final ChatService chatService;
    private final GitHubClient gitHubClient;
    private final BotClient botClient;

    @Autowired
    public GitHubLinkUpdater(
        LinkService linkService,
        GitHubRepositoryService gitHubRepositoryService,
        ChatService chatService,
        GitHubClient gitHubClient,
        BotClient botClient
    ) {
        this.linkService = linkService;
        this.gitHubRepositoryService = gitHubRepositoryService;
        this.chatService = chatService;
        this.gitHubClient = gitHubClient;
        this.botClient = botClient;
    }

    public boolean update(GitHubLink gitHubLink, Link link) {
        try {
            checkByLastActivity(link, gitHubLink);
            checkByLastCommits(link, gitHubLink);
            link.setLastCheckTime(OffsetDateTime.now());
            linkService.update(link);
            return true;
        } catch (GitHubClientException gitHubClientException) {
            return false;
        }
    }

    private void checkByLastActivity(Link link, GitHubLink gitHubLink) throws GitHubClientException {
        RepositoryResponse response = gitHubClient.getRepoInfo(gitHubLink.owner(), gitHubLink.repo());
        if (link.getLastActivity().isBefore(response.lastActivity())) {
            List<Long> chatsIds = findLinkChatsIds(link);
            LinkUpdateRequest request =
                new LinkUpdateRequest(link.getId(), link.getUrl(), "Check new updates", chatsIds);
            botClient.checkUpdate(request);
        }
        updateLastActivity(link, response);
    }

    private void checkByLastCommits(Link link, GitHubLink gitHubLink) throws GitHubClientException {
        GitHubRepository repository = gitHubRepositoryService.findByLink(link);
        if (repository == null) {
            gitHubRepositoryService.add(link);
            repository = gitHubRepositoryService.findByLink(link);
        }
        String date = DateTimeFormatter.ISO_INSTANT.format(repository.getLastCommitDate());
        List<RepositoryCommitsResponse> responses =
            gitHubClient.getRepoCommitsAfterDateTime(gitHubLink.owner(), gitHubLink.repo(), date);
        if (responses.isEmpty()) {
            return;
        }
        LinkUpdateRequest linkUpdateRequest =
            new LinkUpdateRequest(
                link.getId(),
                link.getUrl(),
                createRepositoryCommitUpdateMessage(responses),
                findLinkChatsIds(link)
            );
        botClient.checkUpdate(linkUpdateRequest);
        updateLastActivity(repository, responses.getFirst());
    }

    private List<Long> findLinkChatsIds(Link link) {
        List<Chat> chats = chatService.findAll(link.getUrl());
        List<Long> chatsIds = chats.stream().map(Chat::getId).toList();
        return chatsIds;
    }

    private void updateLastActivity(Link link, RepositoryResponse response) {
        link.setLastActivity(response.lastActivity());
    }

    private void updateLastActivity(GitHubRepository repository, RepositoryCommitsResponse response) {
        repository.setLastCommitDate(response.commit().author().commitDateTime().plusMinutes(1));
        gitHubRepositoryService.update(repository);
    }

    private String createRepositoryCommitUpdateMessage(List<RepositoryCommitsResponse> responses) {
        StringBuilder message = new StringBuilder();
        for (RepositoryCommitsResponse response : responses) {
            message.append("\n-----------------------------\n")
                .append(response.sha())
                .append("\n")
                .append("Message: ")
                .append(response.commit().message())
                .append("\n")
                .append("Author name: ")
                .append(response.commit().author().name())
                .append("\n")
                .append("Commit date: ")
                .append(response.commit().author().commitDateTime());
        }
        return message.toString();
    }
}
