package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.enums.ChatState;
import edu.java.bot.services.ScrapperService;

public class TrackCommand implements Command {
    private final static String COMMAND_NAME = "/track";

    private final String commandDescription;
    private final LinkCommandHandler linkCommandHandler;

    public TrackCommand(String description, ScrapperService scrapperService) {
        commandDescription = description;
        linkCommandHandler = new LinkCommandHandler(scrapperService);
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
        return linkCommandHandler.handle(message, chatId, ChatState.TRACK);
    }
}
