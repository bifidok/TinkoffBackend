package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.User;

public interface Command {
    String name();

    String description();

    SendMessage handle(Update update, User user);

    default BotCommand toApiCommand() {
        return new BotCommand(name(), description());
    }
}
