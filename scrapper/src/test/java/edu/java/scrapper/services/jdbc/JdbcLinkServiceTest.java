package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.LinkNotCreatedException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repository.ChatLinkRepository;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JdbcLinkServiceTest extends IntegrationTest {
    private final long defaultChatId = 123L;
    private List<Link> links;
    @Autowired
    private LinkService linkService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatLinkRepository chatLinkService;

    @BeforeEach
    public void initEach() {
        Link link = new Link(URI.create("http://someUrl"));
        links = new ArrayList<>(Collections.singletonList(link));
        Chat chat = new Chat(defaultChatId, ChatState.DEFAULT);
        chatService.register(chat.getId());
        linkService.add(chat.getId(), link.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnAllLinks() {
        Link link = new Link(URI.create("http://localhost/1"));
        Link link2 = new Link(URI.create("http://localhost/2"));
        linkService.add(defaultChatId, link.getUrl());
        linkService.add(defaultChatId, link2.getUrl());
        links.addAll(List.of(link, link2));

        List<Link> actual = linkService.findAll();

        Assertions.assertEquals(actual, links);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnLinksByChat() {
        List<Link> actual = linkService.findAll(defaultChatId);

        Assertions.assertEquals(actual, links);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddNewLinkAndCreateRelationToChat() {
        Link link = new Link(URI.create("http://localhost/1"));

        linkService.add(defaultChatId, link.getUrl());
        List<Link> actual = chatLinkService.findLinksByChat(new Chat(defaultChatId));

        assertThat(actual.contains(link)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldThrowLinkNotCreatedException_whenRelationToChatExist() {
        Link link = new Link(URI.create("http://localhost/1"));

        linkService.add(defaultChatId, link.getUrl());

        Assertions.assertThrows(LinkNotCreatedException.class, () -> {
            linkService.add(defaultChatId, link.getUrl());
        });
    }
    @Test
    @Transactional
    @Rollback
    public void add_shouldThrowChatNotFoundException_whenChatNotExist() {
        Link link = new Link(URI.create("http://localhost/1"));
        Chat chat = new Chat(32123L);

        Assertions.assertThrows(ChatNotFoundException.class, () -> {
            linkService.add(chat.getId(), link.getUrl());
        });
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveRelationToChat() {
        Link link = links.stream().findAny().get();

        linkService.remove(defaultChatId,link.getUrl());
        List<Chat> chats = chatLinkService.findChatsByLink(link);

        assertThat(chats.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldThrowLinkNotFoundException_whenLinkNotExist() {
        Link link = new Link(URI.create("http://localhost/1"));

        Assertions.assertThrows(LinkNotFoundException.class, () ->{
           linkService.remove(defaultChatId,link.getUrl());
        });
    }
}
