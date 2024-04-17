package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jdbc.JdbcChatLinkRepository;
import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
import edu.java.scrapper.repositories.jdbc.JdbcGitHubRepositoryRepository;
import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.services.GitHubRepositoryServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JdbcGitHubRepositoryServiceTest extends GitHubRepositoryServiceTest {

    @Autowired
    public JdbcGitHubRepositoryServiceTest(
        JdbcChatRepository chatRepository,
        JdbcLinkRepository linkRepository,
        JdbcChatLinkRepository chatLinkRepository,
        JdbcGitHubRepositoryRepository gitHubRepositoryRepository
    ) {
        super(
            new JdbcGitHubRepositoryService(gitHubRepositoryRepository),
            new JdbcLinkService(linkRepository, chatRepository, chatLinkRepository),
            new JdbcChatService(chatRepository, linkRepository, chatLinkRepository)
        );
    }
}
