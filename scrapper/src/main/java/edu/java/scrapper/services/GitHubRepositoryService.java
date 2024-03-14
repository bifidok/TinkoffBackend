package edu.java.scrapper.services;

import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import java.util.List;

public interface GitHubRepositoryService {
    List<GitHubRepository> findAll();
    GitHubRepository findByLink(Link link);
    void add(Link link);
    void remove(GitHubRepository repository);
    void update(GitHubRepository repository);
}
