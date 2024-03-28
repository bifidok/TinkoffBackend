package edu.java.scrapper.services;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public abstract class GitHubRepositoryServiceTest extends IntegrationTest {
    private final GitHubRepositoryService gitHubRepositoryService;
    private final LinkService linkService;
    private final ChatService chatService;

    @Test
    @Transactional
    @Rollback
    public void findByLink() {
        Chat chat = new Chat(222L);
        chatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        linkService.add(chat.getId(), link.getUrl());
        link = linkService.findByUrl(link.getUrl());
        gitHubRepositoryService.add(link);

        GitHubRepository repository = gitHubRepositoryService.findByLink(link);

        assertThat(repository).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Chat chat = new Chat(222L);
        chatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        linkService.add(chat.getId(), link.getUrl());
        link = linkService.findByUrl(link.getUrl());

        gitHubRepositoryService.add(link);
        GitHubRepository repository = gitHubRepositoryService.findByLink(link);

        assertThat(repository).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        Chat chat = new Chat(222L);
        chatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        linkService.add(chat.getId(), link.getUrl());
        link = linkService.findByUrl(link.getUrl());
        gitHubRepositoryService.add(link);
        GitHubRepository repository = gitHubRepositoryService.findByLink(link);

        gitHubRepositoryService.remove(repository);
        GitHubRepository nullRepository = gitHubRepositoryService.findByLink(link);

        assertThat(nullRepository).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        Chat chat = new Chat(222L);
        chatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        linkService.add(chat.getId(), link.getUrl());
        link = linkService.findByUrl(link.getUrl());
        gitHubRepositoryService.add(link);
        GitHubRepository repository = gitHubRepositoryService.findByLink(link);
        OffsetDateTime updatedCommitDateTime = OffsetDateTime.now();
        repository.setLastCommitDate(updatedCommitDateTime);

        gitHubRepositoryService.update(repository);
        repository = gitHubRepositoryService.findByLink(link);

        assertThat(repository.getLastCommitDate().getYear()).isEqualTo(updatedCommitDateTime.getYear());
        assertThat(repository.getLastCommitDate().getMonth()).isEqualTo(updatedCommitDateTime.getMonth());
    }
}
