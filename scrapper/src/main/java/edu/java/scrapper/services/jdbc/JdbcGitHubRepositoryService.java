package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.GitHubRepositoryRepository;
import edu.java.scrapper.services.GitHubRepositoryService;
import java.time.OffsetDateTime;
import org.springframework.transaction.annotation.Transactional;

public class JdbcGitHubRepositoryService implements GitHubRepositoryService {
    private final GitHubRepositoryRepository gitHubRepositoryRepository;

    public JdbcGitHubRepositoryService(
        GitHubRepositoryRepository gitHubRepositoryRepository
    ) {
        this.gitHubRepositoryRepository = gitHubRepositoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public GitHubRepository findByLink(Link link) {
        return gitHubRepositoryRepository.findByLink(link);
    }

    @Override
    @Transactional
    public void add(Link link) {
        GitHubRepository gitHubRepository = new GitHubRepository(link, OffsetDateTime.now());
        gitHubRepositoryRepository.add(gitHubRepository);
    }

    @Override
    @Transactional
    public void remove(GitHubRepository repository) {
        gitHubRepositoryRepository.remove(repository);
    }

    @Override
    @Transactional
    public void update(GitHubRepository repository) {
        gitHubRepositoryRepository.update(repository);
    }
}
