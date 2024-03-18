package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.time.OffsetDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JdbcGitHubRepositoryServiceTest extends IntegrationTest {
    @Autowired
    private JdbcGitHubRepositoryService jdbcGitHubRepositoryService;
    @Autowired
    private JdbcLinkService jdbcLinkService;
    @Autowired
    private JdbcChatService jdbcChatService;

    @Test
    @Transactional
    @Rollback
    public void findByLink() {
        Chat chat = new Chat(222L);
        jdbcChatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        jdbcLinkService.add(chat.getId(), link.getUrl());
        link = jdbcLinkService.findByUrl(link.getUrl());
        jdbcGitHubRepositoryService.add(link);

        GitHubRepository repository = jdbcGitHubRepositoryService.findByLink(link);

        assertThat(repository).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Chat chat = new Chat(222L);
        jdbcChatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        jdbcLinkService.add(chat.getId(), link.getUrl());
        link = jdbcLinkService.findByUrl(link.getUrl());

        jdbcGitHubRepositoryService.add(link);
        GitHubRepository repository = jdbcGitHubRepositoryService.findByLink(link);

        assertThat(repository).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        Chat chat = new Chat(222L);
        jdbcChatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        jdbcLinkService.add(chat.getId(), link.getUrl());
        link = jdbcLinkService.findByUrl(link.getUrl());
        jdbcGitHubRepositoryService.add(link);
        GitHubRepository repository = jdbcGitHubRepositoryService.findByLink(link);

        jdbcGitHubRepositoryService.remove(repository);
        GitHubRepository nullRepository = jdbcGitHubRepositoryService.findByLink(link);

        assertThat(nullRepository).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        Chat chat = new Chat(222L);
        jdbcChatService.register(chat.getId());
        Link link = new Link(URI.create("http://someLink"));
        jdbcLinkService.add(chat.getId(), link.getUrl());
        link = jdbcLinkService.findByUrl(link.getUrl());
        jdbcGitHubRepositoryService.add(link);
        GitHubRepository repository = jdbcGitHubRepositoryService.findByLink(link);
        repository.setLastCommitDate(OffsetDateTime.MAX);

        jdbcGitHubRepositoryService.update(repository);
        repository = jdbcGitHubRepositoryService.findByLink(link);

        assertThat(repository.getLastCommitDate()).isEqualTo(OffsetDateTime.MAX);
    }

}
