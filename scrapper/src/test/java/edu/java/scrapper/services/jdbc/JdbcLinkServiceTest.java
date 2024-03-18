package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.LinkNotCreatedException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.models.Link;
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
public class JdbcLinkServiceTest extends IntegrationTest {
    @Autowired
    private JdbcChatService jdbcChatService;
    @Autowired
    private JdbcLinkService jdbcLinkService;
    @Autowired
    private JdbcChatLinkRepository jdbcChatLinkRepository;
    private final long defaultChatId = 123L;
    private List<Link> links;

    @BeforeEach
    public void initEach() {
        Link link = new Link(URI.create("http://someUrl"));
        links = new ArrayList<>(Collections.singletonList(link));
        Chat chat = new Chat(defaultChatId, ChatState.DEFAULT);
        jdbcChatService.register(chat.getId());
        jdbcLinkService.add(chat.getId(), link.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnAllLinks() {
        Link link = new Link(URI.create("http://localhost/1"));
        Link link2 = new Link(URI.create("http://localhost/2"));
        jdbcLinkService.add(defaultChatId, link.getUrl());
        jdbcLinkService.add(defaultChatId, link2.getUrl());
        links.addAll(List.of(link, link2));

        List<Link> actual = jdbcLinkService.findAll();

        Assertions.assertEquals(actual, links);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnLinksByChat() {
        List<Link> actual = jdbcLinkService.findAll(defaultChatId);

        Assertions.assertEquals(actual, links);
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrl_shouldReturnLink() {
        Link someLink = links.getFirst();

        Link linkByUrl = jdbcLinkService.findByUrl(someLink.getUrl());

        Assertions.assertEquals(someLink, linkByUrl);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddNewLinkAndCreateRelationToChat() {
        Link link = new Link(URI.create("http://localhost/1"));

        jdbcLinkService.add(defaultChatId, link.getUrl());
        List<Link> actual = jdbcChatLinkRepository.findLinksByChat(new Chat(defaultChatId));

        assertThat(actual.contains(link)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldThrowLinkNotCreatedException_whenRelationToChatExist() {
        Link link = new Link(URI.create("http://localhost/1"));

        jdbcLinkService.add(defaultChatId, link.getUrl());

        Assertions.assertThrows(LinkNotCreatedException.class, () -> {
            jdbcLinkService.add(defaultChatId, link.getUrl());
        });
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldThrowChatNotFoundException_whenChatNotExist() {
        Link link = new Link(URI.create("http://localhost/1"));
        Chat chat = new Chat(32123L);

        Assertions.assertThrows(ChatNotFoundException.class, () -> {
            jdbcLinkService.add(chat.getId(), link.getUrl());
        });
    }

    @Test
    @Transactional
    @Rollback
    public void update_shouldUpdateLastActivity() {
        Link link = new Link(URI.create("http://localhost/1"));
        Chat chat = new Chat(32123L);
        jdbcChatService.register(chat.getId());
        jdbcLinkService.add(chat.getId(), link.getUrl());
        link = jdbcLinkService.findByUrl(link.getUrl());
        OffsetDateTime updatedDateTime = OffsetDateTime.MAX;
        link.setLastActivity(updatedDateTime);

        jdbcLinkService.update(link);
        Link updatedLink = jdbcLinkService.findByUrl(link.getUrl());

        assertThat(updatedLink).isNotNull();
        assertThat(updatedLink.getLastActivity()).isEqualTo(updatedDateTime);
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        jdbcLinkService.remove(links.getFirst().getUrl());
        Link link = jdbcLinkService.findByUrl(links.getFirst().getUrl());

        assertThat(link).isNull();
    }
    @Test
    @Transactional
    @Rollback
    public void removeUnused() {
        Chat chat = new Chat(333L);
        jdbcChatService.register(chat.getId());
        Link link = new Link(URI.create("http://unused"));
        jdbcLinkService.add(chat.getId(),link.getUrl());
        link = jdbcLinkService.findByUrl(link.getUrl());
        jdbcChatLinkRepository.remove(chat,link);

        jdbcLinkService.removeUnused();
        link = jdbcLinkService.findByUrl(link.getUrl());

        assertThat(link).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveRelationToChat() {
        Link link = links.stream().findAny().get();

        jdbcLinkService.remove(defaultChatId, link.getUrl());
        List<Chat> chats = jdbcChatLinkRepository.findChatsByLink(link);

        assertThat(chats.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldThrowLinkNotFoundException_whenLinkNotExist() {
        Link link = new Link(URI.create("http://localhost/1"));

        Assertions.assertThrows(LinkNotFoundException.class, () -> {
            jdbcLinkService.remove(defaultChatId, link.getUrl());
        });
    }
}
