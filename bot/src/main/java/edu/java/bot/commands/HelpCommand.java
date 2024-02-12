package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.User;

public class HelpCommand implements Command {
    private final static String COMMAND_NAME = "/help";
    private final static String COMMAND_DESCRIPTION = "shows available commands";
    private final static String HELP_TEXT = "Bot that monitors changes on websites \n"
        + "/start - registration\n"
        + "/help - show available commands \n"
        + "/list - show the list of trackable links\n"
        + "/track - start link monitoring\n"
        + "/untrack - delete link from monitoring";

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
        return new SendMessage(update.message().chat().id(), HELP_TEXT);
    }
}
