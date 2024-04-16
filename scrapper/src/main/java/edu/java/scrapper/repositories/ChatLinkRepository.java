package edu.java.scrapper.repositories;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import java.util.List;

public interface ChatLinkRepository {
    List<Chat> findChatsByLink(Link link);

    List<Link> findLinksByChat(Chat chat);

    void add(Chat chat, Link link);

    void remove(Chat chat, Link link);

    void removeUnusedLinks();
}
