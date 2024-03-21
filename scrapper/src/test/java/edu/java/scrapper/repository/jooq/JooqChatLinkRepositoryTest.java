package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jooq.JooqChatLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqChatRepository;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import edu.java.scrapper.repository.ChatLinkRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JooqChatLinkRepositoryTest extends ChatLinkRepositoryTest {

    @Autowired
    public JooqChatLinkRepositoryTest(
        JooqChatLinkRepository chatLinkRepository,
        JooqLinkRepository linkRepository,
        JooqChatRepository chatRepository
    ) {
        super(chatLinkRepository, linkRepository, chatRepository);
    }
}
