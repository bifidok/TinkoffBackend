package edu.java.scrapper.services;

import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;

public interface GitHubRepositoryService {
    GitHubRepository findByLink(Link link);

    void add(Link link);

    void remove(GitHubRepository repository);

    void update(GitHubRepository repository);
}
