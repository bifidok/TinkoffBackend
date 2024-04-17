package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.jdbc.JdbcGitHubRepositoryRepository;
import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqGitHubRepositoryRepository;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import edu.java.scrapper.repository.GitHubRepositoryRepositoryTest;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.time.OffsetDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JooqGitHubRepositoryRepositoryTest extends GitHubRepositoryRepositoryTest {
    @Autowired
    public JooqGitHubRepositoryRepositoryTest(JooqGitHubRepositoryRepository gitHubRepositoryRepository, JooqLinkRepository linkRepository) {
        super(gitHubRepositoryRepository, linkRepository);
    }
}
