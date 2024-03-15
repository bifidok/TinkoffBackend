package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.LinkNotCreatedException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repository.ChatLinkRepository;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.services.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {
    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;
    private final ChatLinkRepository chatLinkRepository;

    @Autowired
    public JdbcLinkService(
        LinkRepository linkRepository,
        ChatRepository chatRepository,
        ChatLinkRepository chatLinkRepository
    ) {
        this.linkRepository = linkRepository;
        this.chatRepository = chatRepository;
        this.chatLinkRepository = chatLinkRepository;
    }

    @Override
    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    @Override
    public List<Link> findAll(long tgChatId) {
        Chat chat = chatRepository.findById(tgChatId);
        if (chat == null) {
            throw new ChatNotFoundException();
        }
        return chatLinkRepository.findLinksByChat(chat);
    }

    @Override
    public List<Link> findByCheckDateMoreThan(OffsetDateTime dateTime) {
        return linkRepository.findByCheckDateMoreThan(dateTime);
    }

    @Override
    public Link findByUrl(URI url) {
        return linkRepository.findByUrl(url);
    }

    @Override
    public void add(long tgChatId, URI url) {
        Chat chat = chatRepository.findById(tgChatId);
        if (chat == null) {
            throw new ChatNotFoundException();
        }
        Link link = linkRepository.findByUrl(url);
        if (link != null) {
            List<Chat> chats = chatLinkRepository.findChatsByLink(link);
            if (chats.contains(chat)) {
                throw new LinkNotCreatedException("Chat already track this link");
            }
        } else {
            linkRepository.add(new Link(url, OffsetDateTime.now()));
            link = linkRepository.findByUrl(url);
        }
        chatLinkRepository.add(chat, link);
    }

    @Override
    public void update(Link link) {
        linkRepository.update(link);
    }

    @Override
    public void remove(long tgChatId, URI url) {
        Link link = linkRepository.findByUrl(url);
        if (link == null) {
            throw new LinkNotFoundException();
        }
        Chat chat = chatRepository.findById(tgChatId);
        if (chat == null) {
            throw new ChatNotFoundException();
        }
        chatLinkRepository.remove(chat, link);
    }

    @Override
    public void remove(URI url) {
        Link link = linkRepository.findByUrl(url);
        if (link == null) {
            throw new LinkNotFoundException();
        }
        linkRepository.remove(link.getUrl());
    }
}
