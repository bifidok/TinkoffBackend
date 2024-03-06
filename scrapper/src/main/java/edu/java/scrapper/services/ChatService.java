package edu.java.scrapper.services;

import edu.java.scrapper.exceptions.ChatNotCreatedException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.models.Chat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatService {
    private final Map<Integer, Chat> database;

    public ChatService() {
        database = new HashMap<>();
    }

    public Chat findById(int id) {
        return database.get(id);
    }

    public void create(int id) {
        if (database.containsKey(id)) {
            String message = id + " chat already exist";
            log.info(message);
            throw new ChatNotCreatedException(message);
        }
        Chat chat = new Chat(id, new ArrayList<>());
        database.put(chat.getId(), chat);
    }

    public void delete(int id) {
        Chat chat = findById(id);
        if (chat == null) {
            String message = id + " chat not exist";
            log.info(message);
            throw new ChatNotFoundException(message);
        }
        database.remove(chat.getId());
    }

}
