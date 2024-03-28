package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jdbc.JdbcChatLinkRepository;
import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repository.ChatLinkRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JdbcChatLinkRepositoryTest extends ChatLinkRepositoryTest {

    @Autowired
    public JdbcChatLinkRepositoryTest(
        JdbcChatLinkRepository chatLinkRepository,
        JdbcLinkRepository linkRepository,
        JdbcChatRepository chatRepository
    ) {
        super(chatLinkRepository, linkRepository, chatRepository);
    }
}
