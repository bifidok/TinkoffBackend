package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.repositories.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
public abstract class ChatRepositoryTest extends IntegrationTest {
    private final ChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnChats() {
        Chat chat1 = new Chat(1);
        System.out.println(chat1.getStatus().toString());
        Chat chat2 = new Chat(2);
        Chat chat3 = new Chat(3);
        List<Chat> expected = List.of(chat1, chat2, chat3);
        chatRepository.add(chat1);
        chatRepository.add(chat2);
        chatRepository.add(chat3);

        List<Chat> actual = chatRepository.findAll();

        assertThat(expected).isNotNull();
        assertThat(actual).isNotNull();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void findById_shouldReturnChatById() {
        Chat chat1 = new Chat(1);
        chatRepository.add(chat1);

        Chat actual = chatRepository.findById(chat1.getId());

        assertThat(actual.equals(chat1)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void update_shouldChangeChatState() {
        Chat chat = new Chat(1, ChatState.TRACK);
        chatRepository.add(chat);
        chat = chatRepository.findById(chat.getId());
        chat.setStatus(ChatState.UNTRACK);
        chatRepository.update(chat);

        chat = chatRepository.findById(chat.getId());

        assertThat(chat.getStatus()).isEqualTo(ChatState.UNTRACK);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddChat() {
        Chat chat = new Chat(2);

        chatRepository.add(chat);
        List<Chat> list = chatRepository.findAll();

        assertThat(list.contains(chat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveChat() {
        Chat chat = new Chat(2);
        chatRepository.add(chat);

        chatRepository.remove(chat);
        List<Chat> list = chatRepository.findAll();

        assertThat(list.contains(chat)).isFalse();
    }
}
