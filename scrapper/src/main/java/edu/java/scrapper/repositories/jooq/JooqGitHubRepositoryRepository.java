package edu.java.scrapper.repositories.jooq;

import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.GitHubRepositoryRepository;
import edu.java.scrapper.repositories.jooq.generated.tables.records.RepositoriesRecord;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.repositories.jooq.generated.Tables.REPOSITORIES;

@Repository("jooqGitHubRepositoryRepository")
public class JooqGitHubRepositoryRepository implements GitHubRepositoryRepository {
    private static final RecordMapper<RepositoriesRecord, GitHubRepository> RECORD_MAPPER = r -> new GitHubRepository(
        r.getId(),
        r.getLastCommitDate()
    );

    private final DSLContext dslContext;

    @Autowired
    public JooqGitHubRepositoryRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public GitHubRepository findByLink(Link link) {
        GitHubRepository gitHubRepository = dslContext
            .selectFrom(REPOSITORIES)
            .where(REPOSITORIES.LINK_ID.equal(link.getId()))
            .fetchOne(RECORD_MAPPER);
        if (gitHubRepository != null) {
            gitHubRepository.setLink(link);
        }
        return gitHubRepository;
    }

    @Override
    public void add(GitHubRepository repository) {
        dslContext
            .insertInto(REPOSITORIES)
            .set(REPOSITORIES.LINK_ID, repository.getLink().getId())
            .set(REPOSITORIES.LAST_COMMIT_DATE, repository.getLastCommitDate())
            .execute();
    }

    @Override
    public void remove(GitHubRepository repository) {
        dslContext
            .deleteFrom(REPOSITORIES)
            .where(REPOSITORIES.ID.equal(repository.getId()))
            .execute();
    }

    @Override
    public void update(GitHubRepository repository) {
        dslContext
            .update(REPOSITORIES)
            .set(REPOSITORIES.LAST_COMMIT_DATE, repository.getLastCommitDate())
            .where(REPOSITORIES.ID.equal(repository.getId()))
            .execute();
    }
}
