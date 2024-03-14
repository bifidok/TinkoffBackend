package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repository.GitHubRepositoryRepository;
import edu.java.scrapper.services.GitHubRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class JdbcGitHubRepositoryService implements GitHubRepositoryService {
    private final GitHubRepositoryRepository gitHubRepositoryRepository;

    @Autowired
    public JdbcGitHubRepositoryService(GitHubRepositoryRepository gitHubRepositoryRepository) {
        this.gitHubRepositoryRepository = gitHubRepositoryRepository;
    }

    @Override
    public List<GitHubRepository> findAll() {
        return gitHubRepositoryRepository.findAll();
    }

    @Override
    public GitHubRepository findByLink(Link link) {
        return gitHubRepositoryRepository.findByLink(link);
    }

    @Override
    public void add(Link link) {
        GitHubRepository gitHubRepository = new GitHubRepository(link, OffsetDateTime.now());
        gitHubRepositoryRepository.add(gitHubRepository);
    }

    @Override
    public void remove(GitHubRepository repository) {
        gitHubRepositoryRepository.remove(repository);
    }


    @Override
    public void update(GitHubRepository repository) {
        gitHubRepositoryRepository.update(repository);
    }
}
