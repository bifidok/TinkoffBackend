package edu.java.bot.utils;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.utility.BotUtils;

public class UpdateBuilder {
    private UpdateBuilder() {

    }

    public static Update createUpdate(String message) {
        String updateJson =
            String.format("{\"update_id\":0,\"message\":{\"from\":{\"id\":0,\"is_bot\"" +
                ":false,\"first_name\":\"0\",\"username\":\"0\",\"language_code\":\"0\"},\"text\":\"%s\"" +
                ",\"chat\":{\"id\":0,\"type\":\"private\",\"username\":\"0\",\"first_name\":\"0\"}," +
                "\"message_id\":0,\"date\":0}}", message);
        return BotUtils.fromJson(updateJson, Update.class);
    }
}
