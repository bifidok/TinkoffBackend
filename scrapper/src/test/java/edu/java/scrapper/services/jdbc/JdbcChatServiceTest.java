package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.exceptions.ChatNotCreatedException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repository.ChatLinkRepository;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import java.net.URI;
import java.util.List;
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
    private ChatService chatService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private ChatLinkRepository chatLinkService;

    @BeforeEach
    public void initEach() {
        Link link = new Link(defaultLink);
        Chat chat = new Chat(defaultChatId, ChatState.DEFAULT);
        chatService.register(chat.getId());
        linkService.add(chat.getId(), link.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnAllChats() {
        List<Chat> chats = chatService.findAll();

        assertThat(chats.size() == 1).isTrue();
        assertThat(chats.contains(new Chat(defaultChatId))).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnChatsByLink() {
        List<Chat> chats = chatService.findAll(defaultLink);

        assertThat(chats.size() == 1).isTrue();
        assertThat(chats.contains(new Chat(defaultChatId))).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void register_shouldCreateNewChat() {
        Chat chat = new Chat(3333L, ChatState.TRACK);

        chatService.register(chat.getId());
        List<Chat> chats = chatService.findAll();

        assertThat(chats.contains(chat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void register_shouldThrowChatNotCreatedException_whenChatExist() {
        Chat chat = new Chat(defaultChatId, ChatState.TRACK);

        Assertions.assertThrows(ChatNotCreatedException.class, () -> {
            chatService.register(chat.getId());
        });
    }

    @Test
    @Transactional
    @Rollback
    public void unregister_shouldDeleteChatAndRelationToLinks() {
        chatService.unregister(defaultChatId);
        List<Link> links = chatLinkService.findLinksByChat(new Chat(defaultChatId));

        assertThat(links.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void unregister_shouldThrowChatNotFoundException_whenChatNotExist() {
        Assertions.assertThrows(ChatNotFoundException.class, () -> {
            chatService.unregister(3333L);
        });
    }
}
