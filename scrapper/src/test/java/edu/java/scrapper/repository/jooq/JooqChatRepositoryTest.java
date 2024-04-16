package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.repositories.jooq.JooqChatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JooqChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqChatRepository jooqChatRepository;

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnChats() {
        Chat chat1 = new Chat(1);
        System.out.println(chat1.getStatus().toString());
        Chat chat2 = new Chat(2);
        Chat chat3 = new Chat(3);
        List<Chat> expected = List.of(chat1,chat2,chat3);
        jooqChatRepository.add(chat1);
        jooqChatRepository.add(chat2);
        jooqChatRepository.add(chat3);

        List<Chat> actual = jooqChatRepository.findAll();

        assertThat(expected).isNotNull();
        assertThat(actual).isNotNull();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    @Transactional
    @Rollback
    public void findById_shouldReturnChatById() {
        Chat chat1 = new Chat(1);
        jooqChatRepository.add(chat1);

        Chat actual = jooqChatRepository.findById(chat1.getId());

        assertThat(actual.equals(chat1)).isTrue();
    }
    @Test
    @Transactional
    @Rollback
    public void add_shouldAddChat() {
        Chat chat = new Chat(2);

        jooqChatRepository.add(chat);
        List<Chat> list = jooqChatRepository.findAll();

        assertThat(list.contains(chat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveChat() {
        Chat chat = new Chat(2);
        jooqChatRepository.add(chat);

        jooqChatRepository.remove(chat);
        List<Chat> list = jooqChatRepository.findAll();

        assertThat(list.contains(chat)).isFalse();
    }
}
