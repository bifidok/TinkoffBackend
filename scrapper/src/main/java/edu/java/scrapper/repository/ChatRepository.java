package edu.java.scrapper.repository;

import edu.java.scrapper.models.Chat;
import java.util.List;

public interface ChatRepository {
    List<Chat> findAll();
    void add(Chat chat);
    void remove(Chat chat);
}
