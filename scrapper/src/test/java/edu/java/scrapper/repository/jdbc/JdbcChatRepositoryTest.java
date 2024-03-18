package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
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
public class JdbcChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnChats() {
        Chat chat1 = new Chat(1);
        System.out.println(chat1.getStatus().toString());
        Chat chat2 = new Chat(2);
        Chat chat3 = new Chat(3);
        List<Chat> expected = List.of(chat1,chat2,chat3);
        jdbcChatRepository.add(chat1);
        jdbcChatRepository.add(chat2);
        jdbcChatRepository.add(chat3);

        List<Chat> actual = jdbcChatRepository.findAll();

        assertThat(expected).isNotNull();
        assertThat(actual).isNotNull();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    @Transactional
    @Rollback
    public void findById_shouldReturnChatById() {
        Chat chat1 = new Chat(1);
        jdbcChatRepository.add(chat1);

        Chat actual = jdbcChatRepository.findById(chat1.getId());

        assertThat(actual.equals(chat1)).isTrue();
    }
    @Test
    @Transactional
    @Rollback
    public void update_shouldChangeChatState() {
        Chat chat = new Chat(1, ChatState.TRACK);
        jdbcChatRepository.add(chat);
        chat = jdbcChatRepository.findById(chat.getId());
        chat.setStatus(ChatState.UNTRACK);
        jdbcChatRepository.update(chat);

        chat = jdbcChatRepository.findById(chat.getId());

        assertThat(chat.getStatus()).isEqualTo(ChatState.UNTRACK);
    }
    @Test
    @Transactional
    @Rollback
    public void add_shouldAddChat() {
        Chat chat = new Chat(2);

        jdbcChatRepository.add(chat);
        List<Chat> list = jdbcChatRepository.findAll();

        assertThat(list.contains(chat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveChat() {
        Chat chat = new Chat(2);
        jdbcChatRepository.add(chat);

        jdbcChatRepository.remove(chat);
        List<Chat> list = jdbcChatRepository.findAll();

        assertThat(list.contains(chat)).isFalse();
    }
}
