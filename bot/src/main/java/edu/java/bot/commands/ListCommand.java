package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.User;

public class ListCommand implements Command {
    private final static String COMMAND_NAME = "/list";
    private final static String COMMAND_DESCRIPTION = "shows the list of trackable links";

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
        return new SendMessage(update.message().chat().id(), "Not implemented yet");
    }
}
