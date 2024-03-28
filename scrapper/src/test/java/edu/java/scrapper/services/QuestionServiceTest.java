package edu.java.scrapper.services;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public abstract class QuestionServiceTest extends IntegrationTest {
    private final QuestionService questionService;
    private final LinkService linkService;
    private final ChatService chatService;
    private Question baseQuestion;
    private Link baseLink;
    private Chat baseChat;

    @BeforeEach
    public void initEach() {
        baseChat = new Chat(123L);
        chatService.register(baseChat.getId());
        baseLink = new Link(URI.create("http://someLink"));
        linkService.add(baseChat.getId(), baseLink.getUrl());
        baseLink = linkService.findByUrl(baseLink.getUrl());
        baseQuestion = new Question(2323, baseLink);
        questionService.add(baseQuestion);
    }

    @Test
    @Transactional
    @Rollback
    public void findByLink() {
        Question question1 = questionService.findByLink(baseLink);

        assertThat(question1.getId()).isEqualTo(baseQuestion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Question question = questionService.findByLink(baseLink);

        assertThat(question.getId()).isEqualTo(baseQuestion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        questionService.remove(baseQuestion);
        Question question = questionService.findByLink(baseLink);

        assertThat(question).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        baseQuestion.setAnswerCount(5);

        questionService.update(baseQuestion);
        Question question = questionService.findByLink(baseLink);

        assertThat(question.getAnswerCount()).isEqualTo(baseQuestion.getAnswerCount());
    }
}
