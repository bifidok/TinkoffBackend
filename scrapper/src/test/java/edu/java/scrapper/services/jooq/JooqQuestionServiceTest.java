package edu.java.scrapper.services.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import edu.java.scrapper.services.jdbc.JdbcChatService;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JooqQuestionServiceTest extends IntegrationTest {
    @Autowired
    private JooqQuestionService jooqQuestionService;
    @Autowired
    private JooqLinkService jooqLinkService;
    @Autowired
    private JdbcChatService jooqChatService;
    private Question baseQuestion;
    private Link baseLink;
    private Chat baseChat;

    @BeforeEach
    public void initEach() {
        baseChat = new Chat(123L);
        jooqChatService.register(baseChat.getId());
        baseLink = new Link(URI.create("http://someLink"));
        jooqLinkService.add(baseChat.getId(),baseLink.getUrl());
        baseLink = jooqLinkService.findByUrl(baseLink.getUrl());
        baseQuestion = new Question(2323, baseLink);
        jooqQuestionService.add(baseQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void findByLink() {
        Question question1 = jooqQuestionService.findByLink(baseLink);

        assertThat(question1.getId()).isEqualTo(baseQuestion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Question question = jooqQuestionService.findByLink(baseLink);

        assertThat(question.getId()).isEqualTo(baseQuestion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        jooqQuestionService.remove(baseQuestion);
        Question question = jooqQuestionService.findByLink(baseLink);

        assertThat(question).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        baseQuestion.setAnswerCount(5);

        jooqQuestionService.update(baseQuestion);
        Question question = jooqQuestionService.findByLink(baseLink);

        assertThat(question.getAnswerCount()).isEqualTo(baseQuestion.getAnswerCount());
    }
}
