package edu.java.scrapper.services.jooq;

import edu.java.scrapper.exceptions.ChatNotCreatedException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.ChatLinkRepository;
import edu.java.scrapper.repositories.ChatRepository;
import edu.java.scrapper.repositories.LinkRepository;
import edu.java.scrapper.services.ChatService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jooqChatService")
public class JooqChatService implements ChatService {
    private final ChatRepository chatRepository;
    private final LinkRepository linkRepository;
    private final ChatLinkRepository chatLinkRepository;

    @Autowired
    public JooqChatService(
        @Qualifier("jooqChatRepository") ChatRepository chatRepository,
        @Qualifier("jooqLinkRepository") LinkRepository linkRepository,
        @Qualifier("jooqChatLinkRepository") ChatLinkRepository chatLinkRepository
    ) {
        this.chatRepository = chatRepository;
        this.linkRepository = linkRepository;
        this.chatLinkRepository = chatLinkRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> findAll(URI url) {
        Link link = linkRepository.findByUrl(url);
        if (link == null) {
            throw new LinkNotFoundException();
        }
        return chatLinkRepository.findChatsByLink(link);
    }

    @Override
    @Transactional
    public void register(long tgChatId) {
        Chat chat = chatRepository.findById(tgChatId);
        if (chat != null) {
            throw new ChatNotCreatedException("This chat already exist");
        }
        chat = new Chat(tgChatId, ChatState.DEFAULT);
        chatRepository.add(chat);
    }

    @Override
    @Transactional
    public void unregister(long tgChatId) {
        Chat chat = chatRepository.findById(tgChatId);
        if (chat == null) {
            throw new ChatNotFoundException();
        }
        chatRepository.remove(chat);
    }
}
