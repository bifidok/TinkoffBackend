package edu.java.scrapper.repositories;

import edu.java.scrapper.models.Chat;
import java.util.List;

public interface ChatRepository {
    List<Chat> findAll();

    Chat findById(long tgChatId);

    void add(Chat chat);

    void remove(Chat chat);
}
