package edu.java.scrapper.services.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.LinkNotCreatedException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.jooq.JooqChatLinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
public class JooqLinkServiceTest extends IntegrationTest {
    @Autowired
    private JooqChatService jooqChatService;
    @Autowired
    private JooqLinkService jooqLinkService;
    @Autowired
    private JooqChatLinkRepository jooqChatLinkRepository;
    private final long defaultChatId = 123L;
    private List<Link> links;

    @BeforeEach
    public void initEach() {
        Link link = new Link(URI.create("http://someUrl"));
        links = new ArrayList<>(Collections.singletonList(link));
        Chat chat = new Chat(defaultChatId, ChatState.DEFAULT);
        jooqChatService.register(chat.getId());
        jooqLinkService.add(chat.getId(), link.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnAllLinks() {
        Link link = new Link(URI.create("http://localhost/1"));
        Link link2 = new Link(URI.create("http://localhost/2"));
        jooqLinkService.add(defaultChatId, link.getUrl());
        jooqLinkService.add(defaultChatId, link2.getUrl());
        links.addAll(List.of(link, link2));

        List<Link> actual = jooqLinkService.findAll();

        Assertions.assertEquals(actual, links);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnLinksByChat() {
        List<Link> actual = jooqLinkService.findAll(defaultChatId);

        Assertions.assertEquals(actual, links);
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrl_shouldReturnLink() {
        Link someLink = links.getFirst();

        Link linkByUrl = jooqLinkService.findByUrl(someLink.getUrl());

        Assertions.assertEquals(someLink, linkByUrl);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddNewLinkAndCreateRelationToChat() {
        Link link = new Link(URI.create("http://localhost/1"));

        jooqLinkService.add(defaultChatId, link.getUrl());
        List<Link> actual = jooqChatLinkRepository.findLinksByChat(new Chat(defaultChatId));

        assertThat(actual.contains(link)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldThrowLinkNotCreatedException_whenRelationToChatExist() {
        Link link = new Link(URI.create("http://localhost/1"));

        jooqLinkService.add(defaultChatId, link.getUrl());

        Assertions.assertThrows(LinkNotCreatedException.class, () -> {
            jooqLinkService.add(defaultChatId, link.getUrl());
        });
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldThrowChatNotFoundException_whenChatNotExist() {
        Link link = new Link(URI.create("http://localhost/1"));
        Chat chat = new Chat(32123L);

        Assertions.assertThrows(ChatNotFoundException.class, () -> {
            jooqLinkService.add(chat.getId(), link.getUrl());
        });
    }

    @Test
    @Transactional
    @Rollback
    public void update_shouldUpdateLastActivity() {
        Link link = new Link(URI.create("http://localhost/1"));
        Chat chat = new Chat(32123L);
        jooqChatService.register(chat.getId());
        jooqLinkService.add(chat.getId(), link.getUrl());
        link = jooqLinkService.findByUrl(link.getUrl());
        OffsetDateTime updatedDateTime = OffsetDateTime.now();
        link.setLastActivity(updatedDateTime);

        jooqLinkService.update(link);
        Link updatedLink = jooqLinkService.findByUrl(link.getUrl());

        assertThat(updatedLink).isNotNull();
        assertThat(updatedLink.getLastActivity().getYear()).isEqualTo(updatedDateTime.getYear());
        assertThat(updatedLink.getLastActivity().getMonth()).isEqualTo(updatedDateTime.getMonth());
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        jooqLinkService.remove(links.getFirst().getUrl());
        Link link = jooqLinkService.findByUrl(links.getFirst().getUrl());

        assertThat(link).isNull();
    }
    @Test
    @Transactional
    @Rollback
    public void removeUnused() {
        Chat chat = new Chat(333L);
        jooqChatService.register(chat.getId());
        Link link = new Link(URI.create("http://unused"));
        jooqLinkService.add(chat.getId(),link.getUrl());
        link = jooqLinkService.findByUrl(link.getUrl());
        jooqChatLinkRepository.remove(chat,link);

        jooqLinkService.removeUnused();
        link = jooqLinkService.findByUrl(link.getUrl());

        assertThat(link).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveRelationToChat() {
        Link link = links.stream().findAny().get();

        jooqLinkService.remove(defaultChatId, link.getUrl());
        List<Chat> chats = jooqChatLinkRepository.findChatsByLink(link);

        assertThat(chats.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldThrowLinkNotFoundException_whenLinkNotExist() {
        Link link = new Link(URI.create("http://localhost/1"));

        Assertions.assertThrows(LinkNotFoundException.class, () -> {
            jooqLinkService.remove(defaultChatId, link.getUrl());
        });
    }
}
