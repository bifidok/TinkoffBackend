package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JdbcQuestionServiceTest extends IntegrationTest {
    @Autowired
    private JdbcQuestionService jdbcQuestionService;
    @Autowired
    private JdbcLinkService jdbcLinkService;
    @Autowired
    private JooqChatService jdbcChatService;
    private Question baseQuestion;
    private Link baseLink;
    private Chat baseChat;

    @BeforeEach
    public void initEach() {
        baseChat = new Chat(123L);
        jdbcChatService.register(baseChat.getId());
        baseLink = new Link(URI.create("http://someLink"));
        jdbcLinkService.add(baseChat.getId(),baseLink.getUrl());
        baseLink = jdbcLinkService.findByUrl(baseLink.getUrl());
        baseQuestion = new Question(2323, baseLink);
        jdbcQuestionService.add(baseQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void findByLink() {
        Question question1 = jdbcQuestionService.findByLink(baseLink);

        assertThat(question1.getId()).isEqualTo(baseQuestion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Question question = jdbcQuestionService.findByLink(baseLink);

        assertThat(question.getId()).isEqualTo(baseQuestion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        jdbcQuestionService.remove(baseQuestion);
        Question question = jdbcQuestionService.findByLink(baseLink);

        assertThat(question).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        baseQuestion.setAnswerCount(5);

        jdbcQuestionService.update(baseQuestion);
        Question question = jdbcQuestionService.findByLink(baseLink);

        assertThat(question.getAnswerCount()).isEqualTo(baseQuestion.getAnswerCount());
    }
}
