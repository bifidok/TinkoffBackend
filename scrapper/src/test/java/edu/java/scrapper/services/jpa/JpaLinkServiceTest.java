package edu.java.scrapper.services.jpa;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jooq.JooqChatLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqChatRepository;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.repositories.jpa.impl.JpaChatLinkRepositoryImpl;
import edu.java.scrapper.services.LinkServiceTest;
import edu.java.scrapper.services.jooq.JooqChatService;
import edu.java.scrapper.services.jooq.JooqLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JpaLinkServiceTest extends LinkServiceTest {
    @Autowired
    public JpaLinkServiceTest(
        JpaChatRepository chatRepository,
        JpaLinkRepository linkRepository,
        JpaChatLinkRepositoryImpl chatLinkRepository
    ) {
        super(
            new JpaChatService(chatRepository, linkRepository, chatLinkRepository),
            new JpaLinkService(linkRepository, chatRepository, chatLinkRepository),
            chatLinkRepository
        );
    }
}
