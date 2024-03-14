package edu.java.scrapper.repository;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.GitHubRepository;
import java.util.List;

public interface GitHubRepositoryRepository {
    List<GitHubRepository> findAll();
    GitHubRepository findByLink(Link link);
    void add(GitHubRepository repository);
    void remove(GitHubRepository repository);
    void update(GitHubRepository repository);
}
