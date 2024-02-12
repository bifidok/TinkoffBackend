package edu.java.bot.models;

import edu.java.bot.enums.UserState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private int id;
    private long telegramId;

    private UserState state;

    public User(long telegramId, UserState state) {
        this.telegramId = telegramId;
        this.state = state;
    }

    public void resetState() {
        state = UserState.BASIC;
    }
}
