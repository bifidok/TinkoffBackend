package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.User;

public class StartCommand implements Command {
    private final static String COMMAND_NAME = "/start";
    private final static String COMMAND_DESCRIPTION = "say hello";

    @Override
    public String name() {
        return COMMAND_NAME;
    }

    @Override
    public String description() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update, User user) {
        //TODO send request to db to save new user
        return new SendMessage(update.message().chat().id(), "Hello!");
    }
}
