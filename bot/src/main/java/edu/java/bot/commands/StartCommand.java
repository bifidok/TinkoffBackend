package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.exceptions.ScrapperClientException;
import edu.java.bot.services.ScrapperService;

public class StartCommand implements Command {
    private final static String COMMAND_NAME = "/start";

    private final String commandDescription;
    private final ScrapperService scrapperService;

    public StartCommand(String description, ScrapperService userService) {
        commandDescription = description;
        this.scrapperService = userService;
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
    public SendMessage handle(Message message, long chatId) {
        try {
            scrapperService.register(chatId);
        } catch (ScrapperClientException exception) {
            return new SendMessage(chatId, exception.getMessage());
        }
        return new SendMessage(chatId, "Hello!");
    }
}
