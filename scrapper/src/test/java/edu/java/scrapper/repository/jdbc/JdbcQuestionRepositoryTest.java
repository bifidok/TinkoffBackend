package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repositories.jdbc.JdbcQuestionRepository;
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
public class JdbcQuestionRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcQuestionRepository jdbcQuestionRepository;
    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;
    private Link baseLink;
    private Question baseQuestion;

    @BeforeEach
    public void initEach(){
        baseLink = new Link(URI.create("http://someLink"));
        jdbcLinkRepository.add(baseLink);
        baseLink = jdbcLinkRepository.findByUrl(baseLink.getUrl());
        baseQuestion = new Question(2323,baseLink);
        jdbcQuestionRepository.add(baseQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void findByLink() {
        Question question1 = jdbcQuestionRepository.findByLink(baseLink);

        assertThat(question1.getId()).isEqualTo(baseQuestion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Question question = jdbcQuestionRepository.findByLink(baseLink);

        assertThat(question.getId()).isEqualTo(baseQuestion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        jdbcQuestionRepository.remove(baseQuestion);
        Question question = jdbcQuestionRepository.findByLink(baseLink);

        assertThat(question).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        baseQuestion.setAnswerCount(5);

        jdbcQuestionRepository.update(baseQuestion);
        Question question = jdbcQuestionRepository.findByLink(baseLink);

        assertThat(question.getAnswerCount()).isEqualTo(baseQuestion.getAnswerCount());
    }
}
