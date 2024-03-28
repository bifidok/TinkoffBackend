package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.exceptions.ScrapperClientException;
import edu.java.bot.models.Link;
import edu.java.bot.services.ScrapperService;
import java.util.List;

public class ListCommand implements Command {
    private final static String COMMAND_NAME = "/list";

    private final String commandDescription;
    private final ScrapperService scrapperService;

    public ListCommand(String description, ScrapperService userService) {
        this.scrapperService = userService;
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
    public SendMessage handle(Message message, long chatId) {
        try {
            List<Link> links = scrapperService.getAll(chatId);
            StringBuilder builder = new StringBuilder();
            for (Link link : links) {
                builder.append(link.uri().toString()).append("\n");
            }
            return new SendMessage(chatId, builder.toString());
        } catch (ScrapperClientException exception) {
            return new SendMessage(chatId, exception.getMessage());
        }
    }
}
