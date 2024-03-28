package edu.java.scrapper.services.jooq;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jooq.JooqChatLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqChatRepository;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import edu.java.scrapper.services.ChatServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JooqChatServiceTest extends ChatServiceTest {
    @Autowired
    public JooqChatServiceTest(
        JooqChatRepository chatRepository,
        JooqLinkRepository linkRepository,
        JooqChatLinkRepository chatLinkRepository
    ) {
        super(
            new JooqChatService(chatRepository, linkRepository, chatLinkRepository),
            new JooqLinkService(linkRepository, chatRepository, chatLinkRepository),
            chatLinkRepository
        );
    }
}
