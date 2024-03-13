package edu.java.scrapper.services;

import edu.java.scrapper.models.Chat;
import java.net.URI;
import java.util.List;

public interface ChatService {
    List<Chat> findAll();

    List<Chat> findAll(URI url);

    void register(long tgChatId);

    void unregister(long tgChatId);
}
