package edu.java.bot.models;

import edu.java.bot.enums.ChatState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chat {
    private long telegramId;
    private ChatState state;

    public Chat(long telegramId, ChatState state) {
        this.telegramId = telegramId;
        this.state = state;
    }
}
