package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.jooq.JooqGitHubRepositoryRepository;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class JooqGitHubRepositoryRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqGitHubRepositoryRepository jooqGitHubRepositoryRepository;
    @Autowired
    private JooqLinkRepository jooqLinkRepository;
    private Link baseLink;
    private GitHubRepository baseRepository;

    @BeforeEach
    public void initAll() {
        baseLink = new Link(URI.create("http:/localhost/"));
        jooqLinkRepository.add(baseLink);
        baseLink = jooqLinkRepository.findByUrl(baseLink.getUrl());
        baseRepository = new GitHubRepository(baseLink, OffsetDateTime.now());
        jooqGitHubRepositoryRepository.add(baseRepository);
        baseRepository = jooqGitHubRepositoryRepository.findByLink(baseLink);
    }
    @Test
    @Transactional
    @Rollback
    public void findByLink_shouldReturnGitHubRepository() {
        GitHubRepository actual = jooqGitHubRepositoryRepository.findByLink(baseLink);

        assertThat(baseRepository).isEqualTo(actual);
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Link newLink = new Link(URI.create("http:/localhost/some"));
        jooqLinkRepository.add(newLink);
        newLink = jooqLinkRepository.findByUrl(newLink.getUrl());
        GitHubRepository newRepository = new GitHubRepository(newLink, OffsetDateTime.now());

        jooqGitHubRepositoryRepository.add(newRepository);
        GitHubRepository repository = jooqGitHubRepositoryRepository.findByLink(newLink);

        assertThat(repository).isNotNull();
        assertThat(repository.getLink()).isEqualTo(newLink);
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        jooqGitHubRepositoryRepository.remove(baseRepository);

        GitHubRepository repository = jooqGitHubRepositoryRepository.findByLink(baseLink);

        assertThat(repository).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        OffsetDateTime newLastCommitTime = OffsetDateTime.now();
        baseRepository.setLastCommitDate(newLastCommitTime);

        jooqGitHubRepositoryRepository.update(baseRepository);
        GitHubRepository updatedGitHubRepository = jooqGitHubRepositoryRepository.findByLink(baseLink);

        assertThat(newLastCommitTime.getMonth()).isEqualTo(updatedGitHubRepository.getLastCommitDate().getMonth());
        assertThat(newLastCommitTime.getYear()).isEqualTo(updatedGitHubRepository.getLastCommitDate().getYear());
    }
}
