package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.GitHubRepositoryRepository;
import edu.java.scrapper.repositories.LinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public abstract class GitHubRepositoryRepositoryTest extends IntegrationTest {
    private final GitHubRepositoryRepository gitHubRepositoryRepository;
    private final LinkRepository linkRepository;
    private Link baseLink;
    private GitHubRepository baseRepository;

    @BeforeEach
    public void initAll() {
        baseLink = new Link(URI.create("http://github"));
        linkRepository.add(baseLink);
        baseLink = linkRepository.findByUrl(baseLink.getUrl());
        OffsetDateTime lastCommitDate = OffsetDateTime.of(1, 1, 1, 1,
            1, 1, 1, ZoneOffset.UTC
        );
        baseRepository = new GitHubRepository(baseLink, lastCommitDate);
        gitHubRepositoryRepository.add(baseRepository);
        baseRepository = gitHubRepositoryRepository.findByLink(baseLink);
    }

    @Test
    @Transactional
    @Rollback
    public void findByLink_shouldReturnGitHubRepository() {
        GitHubRepository repository = gitHubRepositoryRepository.findByLink(baseLink);

        assertThat(repository).isEqualTo(baseRepository);
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Link newLink = new Link(URI.create("http://newlink"));
        linkRepository.add(newLink);
        newLink = linkRepository.findByUrl(newLink.getUrl());
        GitHubRepository newRepository = new GitHubRepository(newLink, baseRepository.getLastCommitDate());

        gitHubRepositoryRepository.add(newRepository);
        GitHubRepository repository = gitHubRepositoryRepository.findByLink(newLink);

        assertThat(repository).isEqualTo(newRepository);
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        gitHubRepositoryRepository.remove(baseRepository);
        GitHubRepository repository = gitHubRepositoryRepository.findByLink(baseLink);

        assertThat(repository).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        OffsetDateTime newLastCommitDate = OffsetDateTime.of(2, 2, 2, 2,
            2, 2, 2, ZoneOffset.UTC
        );
        baseRepository.setLastCommitDate(newLastCommitDate);

        gitHubRepositoryRepository.update(baseRepository);
        baseRepository = gitHubRepositoryRepository.findByLink(baseLink);

        assertThat(baseRepository.getLastCommitDate().getYear()).isEqualTo(newLastCommitDate.getYear());
        assertThat(baseRepository.getLastCommitDate().getMonth()).isEqualTo(newLastCommitDate.getMonth());
        assertThat(baseRepository.getLastCommitDate().getDayOfMonth()).isEqualTo(newLastCommitDate.getDayOfMonth());
    }
}
