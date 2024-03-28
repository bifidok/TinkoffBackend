package edu.java.scrapper.repositories.jpa.impl;

import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.GitHubRepositoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SuppressWarnings("MultipleStringLiterals")
public class JpaGitHubRepositoryRepositoryImpl implements GitHubRepositoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GitHubRepository findByLink(Link link) {
        return entityManager
            .createQuery("select g from GitHubRepository g where link.id = :linkId", GitHubRepository.class)
            .setParameter("linkId", link.getId())
            .getResultStream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public void add(GitHubRepository repository) {
        entityManager
            .createNativeQuery("insert into repositories (link_id, last_commit_date) "
                + "values (:linkId,:lastCommitDate)")
            .setParameter("linkId", repository.getLink().getId())
            .setParameter("lastCommitDate", repository.getLastCommitDate())
            .executeUpdate();
    }

    @Override
    public void remove(GitHubRepository repository) {
        entityManager
            .createQuery("delete from GitHubRepository where id = :id")
            .setParameter("id", repository.getId())
            .executeUpdate();
    }

    @Override
    public void update(GitHubRepository repository) {
        entityManager
            .createNativeQuery("update repositories set last_commit_date = :lastCommitDate "
                + "where id = :id")
            .setParameter("lastCommitDate", repository.getLastCommitDate())
            .setParameter("id", repository.getId())
            .executeUpdate();
    }
}
