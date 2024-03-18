package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.exceptions.ChatNotCreatedException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.models.Link;
import java.net.URI;
import java.util.List;
import edu.java.scrapper.repositories.jdbc.JdbcChatLinkRepository;
import org.junit.jupiter.api.Assertions;
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
public class JdbcChatServiceTest extends IntegrationTest {
    private final long defaultChatId = 123L;
    private final URI defaultLink = URI.create("http://someUrl");
    @Autowired
    private JdbcChatService jdbcChatService;
    @Autowired
    private JdbcLinkService jdbcLinkService;
    @Autowired
    private JdbcChatLinkRepository jdbcChatLinkRepository;

    @BeforeEach
    public void initEach() {
        Link link = new Link(defaultLink);
        Chat chat = new Chat(defaultChatId, ChatState.DEFAULT);
        jdbcChatService.register(chat.getId());
        jdbcLinkService.add(chat.getId(), link.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnAllChats() {
        List<Chat> chats = jdbcChatService.findAll();

        assertThat(chats.size() == 1).isTrue();
        assertThat(chats.contains(new Chat(defaultChatId))).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnChatsByLink() {
        List<Chat> chats = jdbcChatService.findAll(defaultLink);

        assertThat(chats.size() == 1).isTrue();
        assertThat(chats.contains(new Chat(defaultChatId))).isTrue();
    }
    @Test
    @Transactional
    @Rollback
    public void findById_whenNotNull() {
        Chat chat = jdbcChatService.findById(defaultChatId);

        assertThat(chat).isNotNull();
    }
    @Test
    @Transactional
    @Rollback
    public void findById_whenNull() {
        Chat chat = jdbcChatService.findById(2222);

        assertThat(chat).isNull();
    }
    @Test
    @Transactional
    @Rollback
    public void update_whenChatExist() {
        Chat chat = jdbcChatService.findById(defaultChatId);
        chat.setStatus(ChatState.UNTRACK);
        jdbcChatService.update(chat.getId(),chat.getStatus());
        chat = jdbcChatService.findById(defaultChatId);

        assertThat(chat.getStatus()).isEqualTo(ChatState.UNTRACK);
    }
    @Test
    @Transactional
    @Rollback
    public void update_whenChatNotExist() {
        Assertions.assertThrows(ChatNotFoundException.class, () -> {
            jdbcChatService.update(3821L,ChatState.TRACK);
        });
    }
    @Test
    @Transactional
    @Rollback
    public void register_shouldCreateNewChat() {
        Chat chat = new Chat(3333L, ChatState.TRACK);

        jdbcChatService.register(chat.getId());
        List<Chat> chats = jdbcChatService.findAll();

        assertThat(chats.contains(chat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void register_shouldThrowChatNotCreatedException_whenChatExist() {
        Chat chat = new Chat(defaultChatId, ChatState.TRACK);

        Assertions.assertThrows(ChatNotCreatedException.class, () -> {
            jdbcChatService.register(chat.getId());
        });
    }

    @Test
    @Transactional
    @Rollback
    public void unregister_shouldDeleteChatAndRelationToLinks() {
        jdbcChatService.unregister(defaultChatId);
        List<Link> links = jdbcChatLinkRepository.findLinksByChat(new Chat(defaultChatId));

        assertThat(links.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void unregister_shouldThrowChatNotFoundException_whenChatNotExist() {
        Assertions.assertThrows(ChatNotFoundException.class, () -> {
            jdbcChatService.unregister(3333L);
        });
    }
}
