package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import java.net.URI;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqQuestionRepository;
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
public class JooqQuestionRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqQuestionRepository jooqQuestionRepository;
    @Autowired
    private JooqLinkRepository jooqLinkRepository;
    private Link baseLink;
    private Question baseQuestion;

    @BeforeEach
    public void initEach(){
        baseLink = new Link(URI.create("http://someLink"));
        jooqLinkRepository.add(baseLink);
        baseLink = jooqLinkRepository.findByUrl(baseLink.getUrl());
        baseQuestion = new Question(2323,baseLink);
        jooqQuestionRepository.add(baseQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void findByLink() {
        Question question1 = jooqQuestionRepository.findByLink(baseLink);

        assertThat(question1.getId()).isEqualTo(baseQuestion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Question question = jooqQuestionRepository.findByLink(baseLink);

        assertThat(question.getId()).isEqualTo(baseQuestion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        jooqQuestionRepository.remove(baseQuestion);
        Question question = jooqQuestionRepository.findByLink(baseLink);

        assertThat(question).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        baseQuestion.setAnswerCount(5);

        jooqQuestionRepository.update(baseQuestion);
        Question question = jooqQuestionRepository.findByLink(baseLink);

        assertThat(question.getAnswerCount()).isEqualTo(baseQuestion.getAnswerCount());
    }
}
