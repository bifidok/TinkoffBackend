package edu.java.scrapper.services.jpa;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.ChatLinkRepository;
import edu.java.scrapper.repositories.jdbc.JdbcChatLinkRepository;
import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqChatLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqChatRepository;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.repositories.jpa.impl.JpaChatLinkRepositoryImpl;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.ChatServiceTest;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.services.jdbc.JdbcChatService;
import edu.java.scrapper.services.jdbc.JdbcLinkService;
import edu.java.scrapper.services.jooq.JooqChatService;
import edu.java.scrapper.services.jooq.JooqLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JpaChatServiceTest extends ChatServiceTest {

    @Autowired
    public JpaChatServiceTest(
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
