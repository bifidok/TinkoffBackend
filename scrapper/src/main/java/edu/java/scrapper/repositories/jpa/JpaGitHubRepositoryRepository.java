package edu.java.scrapper.repositories.jpa;

import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.repositories.GitHubRepositoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGitHubRepositoryRepository extends JpaRepository<GitHubRepository, Integer>,
    GitHubRepositoryRepository {
}
