package edu.java.scrapper.services.jpa;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jooq.JooqChatLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqChatRepository;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqQuestionRepository;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.repositories.jpa.JpaQuestionRepository;
import edu.java.scrapper.repositories.jpa.impl.JpaChatLinkRepositoryImpl;
import edu.java.scrapper.services.QuestionServiceTest;
import edu.java.scrapper.services.jooq.JooqChatService;
import edu.java.scrapper.services.jooq.JooqLinkService;
import edu.java.scrapper.services.jooq.JooqQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JpaQuestionServiceTest extends QuestionServiceTest {
    @Autowired
    public JpaQuestionServiceTest(
        JpaQuestionRepository questionRepository,
        JpaChatRepository chatRepository,
        JpaLinkRepository linkRepository,
        JpaChatLinkRepositoryImpl chatLinkRepository
    ) {
        super(
            new JpaQuestionService(questionRepository),
            new JpaLinkService(linkRepository, chatRepository, chatLinkRepository),
            new JpaChatService(chatRepository, linkRepository, chatLinkRepository)
        );
    }
}
