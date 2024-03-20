package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import java.net.URI;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.repositories.jpa.JpaQuestionRepository;
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
public class JpaQuestionRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaQuestionRepository jpaQuestionRepository;
    @Autowired
    private JpaLinkRepository jpaLinkRepository;
    private Link baseLink;
    private Question baseQuestion;

    @BeforeEach
    public void initEach() {
        baseLink = new Link(URI.create("http://question"));
        jpaLinkRepository.add(baseLink);
        baseLink = jpaLinkRepository.findByUrl(baseLink.getUrl());
        baseQuestion = new Question(123L, baseLink);
        jpaQuestionRepository.add(baseQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void findByLink() {
        Question question = jpaQuestionRepository.findByLink(baseLink);

        assertThat(question).isEqualTo(baseQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Link newLink = new Link(URI.create("http://newlink"));
        jpaLinkRepository.add(newLink);
        newLink = jpaLinkRepository.findByUrl(newLink.getUrl());
        Question newQuestion = new Question(2222L,newLink);

        jpaQuestionRepository.add(newQuestion);
        Question question = jpaQuestionRepository.findByLink(newLink);

        assertThat(question).isEqualTo(newQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        jpaQuestionRepository.remove(baseQuestion);
        Question question = jpaQuestionRepository.findByLink(baseLink);

        assertThat(question).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        int newAnswerCount = 11;
        baseQuestion.setAnswerCount(newAnswerCount);

        jpaQuestionRepository.update(baseQuestion);
        Question question = jpaQuestionRepository.findByLink(baseLink);

        assertThat(question.getAnswerCount()).isEqualTo(newAnswerCount);
    }
}
