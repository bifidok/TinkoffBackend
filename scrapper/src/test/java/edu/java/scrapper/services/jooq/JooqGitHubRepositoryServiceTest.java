package edu.java.scrapper.services.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JooqGitHubRepositoryServiceTest extends IntegrationTest {
    @Autowired
    private JooqGitHubRepositoryService jooqGitHubRepositoryService;
    @Autowired
    private JooqLinkService jooqLinkService;
    @Autowired
    private JooqChatService jooqChatService;

    @Test
    @Transactional
    @Rollback
    public void findByLink() {
        Chat chat = new Chat(222L);
        jooqChatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        jooqLinkService.add(chat.getId(), link.getUrl());
        link = jooqLinkService.findByUrl(link.getUrl());
        jooqGitHubRepositoryService.add(link);

        GitHubRepository repository = jooqGitHubRepositoryService.findByLink(link);

        assertThat(repository).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Chat chat = new Chat(222L);
        jooqChatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        jooqLinkService.add(chat.getId(), link.getUrl());
        link = jooqLinkService.findByUrl(link.getUrl());

        jooqGitHubRepositoryService.add(link);
        GitHubRepository repository = jooqGitHubRepositoryService.findByLink(link);

        assertThat(repository).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        Chat chat = new Chat(222L);
        jooqChatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        jooqLinkService.add(chat.getId(), link.getUrl());
        link = jooqLinkService.findByUrl(link.getUrl());
        jooqGitHubRepositoryService.add(link);
        GitHubRepository repository = jooqGitHubRepositoryService.findByLink(link);

        jooqGitHubRepositoryService.remove(repository);
        GitHubRepository nullRepository = jooqGitHubRepositoryService.findByLink(link);

        assertThat(nullRepository).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        Chat chat = new Chat(222L);
        jooqChatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        jooqLinkService.add(chat.getId(), link.getUrl());
        link = jooqLinkService.findByUrl(link.getUrl());
        jooqGitHubRepositoryService.add(link);
        GitHubRepository repository = jooqGitHubRepositoryService.findByLink(link);
        OffsetDateTime updatedCommitDateTime = OffsetDateTime.now();
        repository.setLastCommitDate(updatedCommitDateTime);

        jooqGitHubRepositoryService.update(repository);
        repository = jooqGitHubRepositoryService.findByLink(link);

        assertThat(repository.getLastCommitDate().getYear()).isEqualTo(updatedCommitDateTime.getYear());
        assertThat(repository.getLastCommitDate().getMonth()).isEqualTo(updatedCommitDateTime.getMonth());
    }

}
