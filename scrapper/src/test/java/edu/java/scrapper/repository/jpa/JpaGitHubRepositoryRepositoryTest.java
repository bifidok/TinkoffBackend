package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.jpa.JpaGitHubRepositoryRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JpaGitHubRepositoryRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaGitHubRepositoryRepository jpaGitHubRepositoryRepository;
    @Autowired
    private JpaLinkRepository jpaLinkRepository;
    private Link baseLink;
    private GitHubRepository baseRepository;

    @BeforeEach
    public void initAll() {
        baseLink = new Link(URI.create("http://github"));
        jpaLinkRepository.add(baseLink);
        baseLink = jpaLinkRepository.findByUrl(baseLink.getUrl());
        OffsetDateTime lastCommitDate = OffsetDateTime.of(1, 1, 1, 1,
            1, 1, 1, ZoneOffset.UTC);
        baseRepository = new GitHubRepository(baseLink,lastCommitDate);
        jpaGitHubRepositoryRepository.add(baseRepository);
        baseRepository = jpaGitHubRepositoryRepository.findByLink(baseLink);
    }

    @Test
    @Transactional
    @Rollback
    public void findByLink_shouldReturnGitHubRepository() {
        GitHubRepository repository = jpaGitHubRepositoryRepository.findByLink(baseLink);

        assertThat(repository).isEqualTo(baseRepository);
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Link newLink = new Link(URI.create("http://newlink"));
        jpaLinkRepository.add(newLink);
        newLink = jpaLinkRepository.findByUrl(newLink.getUrl());
        GitHubRepository newRepository = new GitHubRepository(newLink,baseRepository.getLastCommitDate());

        jpaGitHubRepositoryRepository.add(newRepository);
        GitHubRepository repository = jpaGitHubRepositoryRepository.findByLink(newLink);

        assertThat(repository).isEqualTo(newRepository);
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        jpaGitHubRepositoryRepository.remove(baseRepository);
        GitHubRepository repository = jpaGitHubRepositoryRepository.findByLink(baseLink);

        assertThat(repository).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        OffsetDateTime newLastCommitDate = OffsetDateTime.of(2,2,2,2,
            2,2,2,ZoneOffset.UTC);
        baseRepository.setLastCommitDate(newLastCommitDate);

        jpaGitHubRepositoryRepository.update(baseRepository);
        baseRepository = jpaGitHubRepositoryRepository.findByLink(baseLink);

        assertThat(baseRepository.getLastCommitDate()).isEqualTo(newLastCommitDate);
    }
}
