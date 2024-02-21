package edu.java.bot.services;

import edu.java.bot.models.User;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserService {
    private Map<Long, User> database;

    @PostConstruct
    public void start() {
        database = new HashMap<>();
    }

    public User findByTelegramId(long id) {
        return database.get(id);
    }

    public void create(User user) {
        if (database.containsKey(user.getTelegramId())) {
            log.info(user.getTelegramId() + " already exist");
            return;
        }
        database.put(user.getTelegramId(), user);
    }

    public void update(User user) {
        database.put(user.getTelegramId(), user);
    }
}
