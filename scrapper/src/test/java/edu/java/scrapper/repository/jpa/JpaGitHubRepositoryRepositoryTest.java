package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jpa.JpaGitHubRepositoryRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.repository.GitHubRepositoryRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JpaGitHubRepositoryRepositoryTest extends GitHubRepositoryRepositoryTest {
    @Autowired
    public JpaGitHubRepositoryRepositoryTest(
        JpaGitHubRepositoryRepository gitHubRepositoryRepository,
        JpaLinkRepository linkRepository
    ) {
        super(gitHubRepositoryRepository, linkRepository);
    }
}
