package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String name();

    String description();

    SendMessage handle(Message message, long userId);

    default BotCommand toApiCommand() {
        return new BotCommand(name(), description());
    }
}
