package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.ChatLinkRepository;
import edu.java.scrapper.repositories.ChatRepository;
import edu.java.scrapper.repositories.LinkRepository;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public abstract class ChatLinkRepositoryTest extends IntegrationTest {
    private final ChatLinkRepository chatLinkRepository;
    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;
    private Chat baseChat;
    private Link baseLink;

    @BeforeEach
    public void initEach() {
        baseChat = new Chat(123L);
        baseLink = new Link(URI.create("http://link"));
        linkRepository.add(baseLink);
        chatRepository.add(baseChat);
        baseLink = linkRepository.findByUrl(baseLink.getUrl());
        chatLinkRepository.add(baseChat, baseLink);
    }

    @Test
    @Transactional
    @Rollback
    public void findChatsByLink() {
        List<Chat> chats = chatLinkRepository.findChatsByLink(baseLink);

        assertThat(chats.contains(baseChat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findLinksByChat() {
        List<Link> links = chatLinkRepository.findLinksByChat(baseChat);

        assertThat(links.contains(baseLink)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Link newLink = new Link(URI.create("http://123"));
        Chat newChat = new Chat(22L);
        linkRepository.add(newLink);
        chatRepository.add(newChat);
        newLink = linkRepository.findByUrl(newLink.getUrl());

        chatLinkRepository.add(newChat, newLink);
        List<Chat> chats = chatLinkRepository.findChatsByLink(newLink);
        List<Link> links = chatLinkRepository.findLinksByChat(newChat);

        assertThat(chats.contains(newChat)).isTrue();
        assertThat(links.contains(newLink)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void remove() {
        chatLinkRepository.remove(baseChat, baseLink);
        List<Chat> chats = chatLinkRepository.findChatsByLink(baseLink);
        List<Link> links = chatLinkRepository.findLinksByChat(baseChat);

        assertThat(chats.contains(baseChat)).isFalse();
        assertThat(links.contains(baseLink)).isFalse();
    }

    @Test
    @Transactional
    @Rollback
    public void removeUnusedLinks() {
        chatRepository.remove(baseChat);

        chatLinkRepository.removeUnusedLinks();
        Link link = linkRepository.findByUrl(baseLink.getUrl());

        assertThat(link).isNull();
    }
}
