package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Chat;
import java.util.List;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JpaChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaChatRepository jpaChatRepository;
    private Chat baseChat;

    @BeforeEach
    public void initEach() {
        baseChat = new Chat(123L);
        jpaChatRepository.add(baseChat);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnChats() {
        List<Chat> chats = jpaChatRepository.findAll();

        assertThat(chats.contains(baseChat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findById_shouldReturnChatById() {
        Chat chat = jpaChatRepository.findById(baseChat.getId());

        assertThat(chat).isEqualTo(baseChat);
    }

    @Test
    @Transactional
    @Rollback
    public void update_shouldChangeChatState() {
        baseChat.setStatus(ChatState.TRACK);

        jpaChatRepository.update(baseChat);
        Chat chat = jpaChatRepository.findById(baseChat.getId());

        assertThat(chat.getStatus()).isEqualTo(ChatState.TRACK);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddChat() {
        Chat newChat = new Chat(222);

        jpaChatRepository.add(newChat);
        Chat chat = jpaChatRepository.findById(newChat.getId());

        assertThat(chat).isEqualTo(newChat);
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveChat() {
        jpaChatRepository.remove(baseChat);
        Chat chat = jpaChatRepository.findById(baseChat.getId());

        assertThat(chat).isNull();
    }
}
