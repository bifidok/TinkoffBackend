package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import edu.java.bot.services.UserService;
import java.util.List;

public class ListCommand implements Command {
    private final static String COMMAND_NAME = "/list";

    private final String commandDescription;
    private final UserService userService;

    public ListCommand(String description, UserService userService) {
        this.userService = userService;
        commandDescription = description;
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
        List<Link> links = user.getLinks();
        StringBuilder builder = new StringBuilder();
        for (Link link : links) {
            builder.append(link.uri().toString()).append("\n");
        }
        return new SendMessage(userId, builder.toString());
    }
}
