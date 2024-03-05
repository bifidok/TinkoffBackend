package edu.java.scrapper.services;

import edu.java.scrapper.exceptions.ChatNotCreatedException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.models.TelegramChat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatService {
    private final Map<Integer, TelegramChat> database;

    public ChatService() {
        database = new HashMap<>();
    }

    public TelegramChat findById(int id) {
        return database.get(id);
    }

    public void create(int id) {
        if (database.containsKey(id)) {
            String message = id + " chat already exist";
            log.info(message);
            throw new ChatNotCreatedException(message);
        }
        TelegramChat telegramChat = new TelegramChat(id, new ArrayList<>());
        database.put(telegramChat.getId(), telegramChat);
    }

    public void delete(int id) {
        TelegramChat telegramChat = findById(id);
        if (telegramChat == null) {
            String message = id + " chat not exist";
            log.info(message);
            throw new ChatNotFoundException(message);
        }
        database.remove(telegramChat.getId());
    }

}
