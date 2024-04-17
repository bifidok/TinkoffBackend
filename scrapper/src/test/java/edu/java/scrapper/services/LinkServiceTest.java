package edu.java.scrapper.services;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.LinkNotCreatedException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.ChatLinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public abstract class LinkServiceTest extends IntegrationTest {
    private final ChatService chatService;
    private final LinkService linkService;
    private final ChatLinkRepository chatLinkRepository;
    private final long defaultChatId = 123L;
    private List<Link> links;

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
    public void findByUrl_shouldReturnLink() {
        Link someLink = links.getFirst();

        Link linkByUrl = linkService.findByUrl(someLink.getUrl());

        Assertions.assertEquals(someLink, linkByUrl);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddNewLinkAndCreateRelationToChat() {
        Link link = new Link(URI.create("http://localhost/1"));

        linkService.add(defaultChatId, link.getUrl());
        List<Link> actual = chatLinkRepository.findLinksByChat(new Chat(defaultChatId));

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
    public void update_shouldUpdateLastActivity() {
        Link link = new Link(URI.create("http://localhost/1"));
        Chat chat = new Chat(32123L);
        chatService.register(chat.getId());
        linkService.add(chat.getId(), link.getUrl());
        link = linkService.findByUrl(link.getUrl());
        OffsetDateTime updatedDateTime = OffsetDateTime.now();
        link.setLastActivity(updatedDateTime);

        linkService.update(link);
        Link updatedLink = linkService.findByUrl(link.getUrl());

        assertThat(updatedLink).isNotNull();
        assertThat(updatedLink.getLastActivity().getYear()).isEqualTo(updatedDateTime.getYear());
        assertThat(updatedLink.getLastActivity().getMonth()).isEqualTo(updatedDateTime.getMonth());
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        linkService.remove(links.getFirst().getUrl());
        Link link = linkService.findByUrl(links.getFirst().getUrl());

        assertThat(link).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void removeUnused() {
        Chat chat = new Chat(333L);
        chatService.register(chat.getId());
        Link link = new Link(URI.create("http://unused"));
        linkService.add(chat.getId(), link.getUrl());
        link = linkService.findByUrl(link.getUrl());
        chatLinkRepository.remove(chat, link);

        linkService.removeUnused();
        link = linkService.findByUrl(link.getUrl());

        assertThat(link).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveRelationToChat() {
        Link link = links.stream().findAny().get();

        linkService.remove(defaultChatId, link.getUrl());
        List<Chat> chats = chatLinkRepository.findChatsByLink(link);

        assertThat(chats.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldThrowLinkNotFoundException_whenLinkNotExist() {
        Link link = new Link(URI.create("http://localhost/1"));

        Assertions.assertThrows(LinkNotFoundException.class, () -> {
            linkService.remove(defaultChatId, link.getUrl());
        });
    }
}
