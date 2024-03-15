package edu.java.scrapper.repository;

import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;

public interface GitHubRepositoryRepository {
    GitHubRepository findByLink(Link link);

    void add(GitHubRepository repository);

    void remove(GitHubRepository repository);

    void update(GitHubRepository repository);
}
