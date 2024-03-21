package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.repositories.jpa.impl.JpaChatLinkRepositoryImpl;
import edu.java.scrapper.repository.ChatLinkRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JpaChatLinkRepositoryTest extends ChatLinkRepositoryTest {

    @Autowired
    public JpaChatLinkRepositoryTest(
        JpaChatLinkRepositoryImpl chatLinkRepository,
        JpaLinkRepository linkRepository,
        JpaChatRepository chatRepository
    ) {
        super(chatLinkRepository, linkRepository, chatRepository);
    }
}
