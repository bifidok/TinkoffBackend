package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.enums.UserState;
import edu.java.bot.models.User;
import edu.java.bot.services.UserService;

public class UntrackCommand implements Command {
    private final static String COMMAND_NAME = "/untrack";

    private final String commandDescription;
    private final UserService userService;
    private final LinkCommandHandler linkCommandHandler;

    public UntrackCommand(String description, UserService userService) {
        this.userService = userService;
        commandDescription = description;
        linkCommandHandler = new LinkCommandHandler(userService);
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
        User user = userService.findByTelegramId(userId);
        if (user == null) {
            return new SendMessage(userId, "You are not in database");
        }
        return linkCommandHandler.handle(message, user, UserState.UNTRACK);
    }
}
