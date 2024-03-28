package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.repositories.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
public abstract class ChatRepositoryTest extends IntegrationTest {
    private final ChatRepository chatRepository;
    private Chat baseChat;

    @BeforeEach
    public void initEach() {
        baseChat = new Chat(123L);
        chatRepository.add(baseChat);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnChats() {
        List<Chat> chats = chatRepository.findAll();

        assertThat(chats.contains(baseChat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findById_shouldReturnChatById() {
        Chat chat = chatRepository.findById(baseChat.getId());

        assertThat(chat).isEqualTo(baseChat);
    }

    @Test
    @Transactional
    @Rollback
    public void update_shouldChangeChatState() {
        baseChat.setStatus(ChatState.TRACK);

        chatRepository.update(baseChat);
        Chat chat = chatRepository.findById(baseChat.getId());

        assertThat(chat.getStatus()).isEqualTo(ChatState.TRACK);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddChat() {
        Chat newChat = new Chat(222);

        chatRepository.add(newChat);
        Chat chat = chatRepository.findById(newChat.getId());

        assertThat(chat).isEqualTo(newChat);
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveChat() {
        chatRepository.remove(baseChat);
        Chat chat = chatRepository.findById(baseChat.getId());

        assertThat(chat).isNull();
    }
}
