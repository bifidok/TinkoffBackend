package edu.java.scrapper.services;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import java.net.URI;
import java.util.List;

public interface ChatService {
    List<Chat> findAll();

    List<Chat> findAll(URI url);

    Chat findById(long tgChatId);

    void update(long tgChatId, ChatState state);

    void register(long tgChatId);

    void unregister(long tgChatId);
}
