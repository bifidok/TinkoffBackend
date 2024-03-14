package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JdbcGitHubRepositoryRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcGitHubRepositoryRepository jdbcGitHubRepositoryRepository;
    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;
    private Link baseLink;
    private GitHubRepository baseRepository;

    @BeforeEach
    public void initAll() {
        baseLink = new Link(URI.create("http:/localhost/"));
        jdbcLinkRepository.add(baseLink);
        baseLink = jdbcLinkRepository.findByUrl(baseLink.getUrl());
        baseRepository = new GitHubRepository(baseLink, OffsetDateTime.now());
        jdbcGitHubRepositoryRepository.add(baseRepository);
        baseRepository = jdbcGitHubRepositoryRepository.findByLink(baseLink);
    }
    @Test
    @Transactional
    @Rollback
    public void findAll(){
        List<GitHubRepository> repositories = jdbcGitHubRepositoryRepository.findAll();

        assertThat(repositories.contains(baseRepository)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findByLink_shouldReturnGitHubRepository() {
        GitHubRepository actual = jdbcGitHubRepositoryRepository.findByLink(baseLink);

        assertThat(baseRepository).isEqualTo(actual);
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Link newLink = new Link(URI.create("http:/localhost/some"));
        jdbcLinkRepository.add(newLink);
        newLink = jdbcLinkRepository.findByUrl(newLink.getUrl());
        GitHubRepository newRepository = new GitHubRepository(newLink, OffsetDateTime.now());

        jdbcGitHubRepositoryRepository.add(newRepository);
        GitHubRepository repository = jdbcGitHubRepositoryRepository.findByLink(newLink);

        assertThat(repository).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        jdbcGitHubRepositoryRepository.remove(baseRepository);

        List<GitHubRepository> list = jdbcGitHubRepositoryRepository.findAll();

        assertThat(list.contains(baseRepository)).isFalse();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        OffsetDateTime lastCommitTime = baseRepository.getLastCommitDate();
        OffsetDateTime newLastCommitTime = OffsetDateTime.MAX;
        baseRepository.setLastCommitDate(newLastCommitTime);

        jdbcGitHubRepositoryRepository.update(baseRepository);
        List<GitHubRepository> list = jdbcGitHubRepositoryRepository.findAll();
        GitHubRepository updatedGitHubRepository =
            list.stream().filter(gh -> gh.getId() == baseRepository.getId()).findFirst().orElse(null);

        assertThat(newLastCommitTime).isEqualTo(updatedGitHubRepository.getLastCommitDate());
    }
}