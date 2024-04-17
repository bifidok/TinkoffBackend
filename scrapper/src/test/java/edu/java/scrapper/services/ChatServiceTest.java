package edu.java.scrapper.services;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.exceptions.ChatNotCreatedException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.ChatLinkRepository;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public abstract class ChatServiceTest extends IntegrationTest {
    private final ChatService chatService;
    private final LinkService linkService;
    private final ChatLinkRepository chatLinkRepository;
    private Chat baseChat;
    private Link baseLink;

    @BeforeEach
    public void initEach() {
        baseLink = new Link(URI.create("http://link"));
        baseChat = new Chat(123, ChatState.DEFAULT);
        chatService.register(baseChat.getId());
        linkService.add(baseChat.getId(), baseLink.getUrl());
        baseLink = linkService.findByUrl(baseLink.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnAllChats() {
        List<Chat> chats = chatService.findAll();

        assertThat(chats.contains(baseChat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnChatsByLink() {
        List<Chat> chats = chatService.findAll(baseLink.getUrl());

        assertThat(chats.size() == 1).isTrue();
        assertThat(chats.contains(baseChat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findById_whenNotNull() {
        Chat chat = chatService.findById(baseChat.getId());

        assertThat(chat).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findById_whenNull() {
        Chat chat = chatService.findById(2222);

        assertThat(chat).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void update_whenChatExist() {
        baseChat.setStatus(ChatState.TRACK);

        chatService.update(baseChat.getId(), baseChat.getStatus());
        Chat chat = chatService.findById(baseChat.getId());

        AssertionsForClassTypes.assertThat(chat.getStatus()).isEqualTo(ChatState.TRACK);
    }

    @Test
    @Transactional
    @Rollback
    public void update_whenChatNotExist() {
        Assertions.assertThrows(ChatNotFoundException.class, () -> {
            chatService.update(3821L, ChatState.TRACK);
        });
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
        Chat chat = new Chat(baseChat.getId(), ChatState.TRACK);

        Assertions.assertThrows(ChatNotCreatedException.class, () -> {
            chatService.register(chat.getId());
        });
    }

    @Test
    @Transactional
    @Rollback
    public void unregister_shouldDeleteChatAndRelationToLinks() {
        chatService.unregister(baseChat.getId());
        List<Link> links = chatLinkRepository.findLinksByChat(baseChat);
        Chat chat = chatService.findById(baseChat.getId());

        assertThat(links.isEmpty()).isTrue();
        assertThat(chat).isNull();
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
