package edu.java.bot.models;

import edu.java.bot.enums.UserState;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long telegramId;
    private UserState state;
    private List<Link> links;

    public User(long telegramId, UserState state) {
        this.telegramId = telegramId;
        this.state = state;
    }

    public void resetState() {
        state = UserState.DEFAULT;
    }

    public List<Link> getLinks() {
        if (links == null) {
            links = new ArrayList<>();
        }
        return links;
    }
}
