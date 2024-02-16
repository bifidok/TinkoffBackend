package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.enums.UserState;
import edu.java.bot.models.User;
import edu.java.bot.services.UserService;

public class StartCommand implements Command {
    private final static String COMMAND_NAME = "/start";

    private final String commandDescription;
    private final UserService userService;

    public StartCommand(String description, UserService userService) {
        commandDescription = description;
        this.userService = userService;
    }

    @Override
    public String name() {
        return COMMAND_NAME;
    }

    @Override
    public String description() {
        return commandDescription;
    }

    @Override
    public SendMessage handle(Message message, long userId) {
        userService.create(new User(userId, UserState.DEFAULT));
        return new SendMessage(userId, "Hello!");
    }
}
