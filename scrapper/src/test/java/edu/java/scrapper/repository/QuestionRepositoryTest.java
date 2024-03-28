package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import edu.java.scrapper.repositories.LinkRepository;
import edu.java.scrapper.repositories.QuestionRepository;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public abstract class QuestionRepositoryTest extends IntegrationTest {
    private final QuestionRepository questionRepository;
    private final LinkRepository linkRepository;
    private Link baseLink;
    private Question baseQuestion;

    @BeforeEach
    public void initEach() {
        baseLink = new Link(URI.create("http://question"));
        linkRepository.add(baseLink);
        baseLink = linkRepository.findByUrl(baseLink.getUrl());
        baseQuestion = new Question(123L, baseLink);
        questionRepository.add(baseQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void findByLink() {
        Question question = questionRepository.findByLink(baseLink);

        assertThat(question).isEqualTo(baseQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Link newLink = new Link(URI.create("http://newlink"));
        linkRepository.add(newLink);
        newLink = linkRepository.findByUrl(newLink.getUrl());
        Question newQuestion = new Question(2222L, newLink);

        questionRepository.add(newQuestion);
        Question question = questionRepository.findByLink(newLink);

        assertThat(question).isEqualTo(newQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        questionRepository.remove(baseQuestion);
        Question question = questionRepository.findByLink(baseLink);

        assertThat(question).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        int newAnswerCount = 11;
        baseQuestion.setAnswerCount(newAnswerCount);

        questionRepository.update(baseQuestion);
        Question question = questionRepository.findByLink(baseLink);

        assertThat(question.getAnswerCount()).isEqualTo(newAnswerCount);
    }
}
