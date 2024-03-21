package edu.java.scrapper.services.jooq;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jooq.JooqChatLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqChatRepository;
import edu.java.scrapper.repositories.jooq.JooqGitHubRepositoryRepository;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import edu.java.scrapper.services.GitHubRepositoryServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JooqGitHubRepositoryServiceTest extends GitHubRepositoryServiceTest {
    @Autowired
    public JooqGitHubRepositoryServiceTest(
        JooqChatRepository chatRepository,
        JooqLinkRepository linkRepository,
        JooqChatLinkRepository chatLinkRepository,
        JooqGitHubRepositoryRepository gitHubRepositoryRepository
    ) {
        super(
            new JooqGitHubRepositoryService(gitHubRepositoryRepository),
            new JooqLinkService(linkRepository, chatRepository, chatLinkRepository),
            new JooqChatService(chatRepository, linkRepository, chatLinkRepository)
        );
    }
}
