package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.enums.ChatState;
import edu.java.bot.exceptions.ScrapperClientException;
import edu.java.bot.models.Chat;
import edu.java.bot.models.Link;
import edu.java.bot.services.ScrapperService;
import edu.java.bot.validators.LinkValidator;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LinkCommandHandler {
    private final static String CHAT_NOT_EXIST = "I dont recognize you";
    private final static String WAIT_FOR_LINK = "Send a link";
    private final static String LINK_PARSE_ERROR = "Cant parse this link. Try another!";
    private final static String FINAL_MESSAGE = "Ok!";

    private final ScrapperService scrapperService;

    public LinkCommandHandler(ScrapperService userService) {
        this.scrapperService = userService;
    }

    public SendMessage handle(Message message, long chatId, ChatState chatState) {
        try {
            Chat chat = scrapperService.findChat(chatId);
            if (chat == null) {
                return new SendMessage(chatId, CHAT_NOT_EXIST);
            }
            if (message.text().equals(chatState.getCommandName())) {
                chat.setState(chatState);
                scrapperService.update(chat);
                return new SendMessage(chat.getTelegramId(), WAIT_FOR_LINK);
            }
            return processLink(message.text(), chat);
        } catch (ScrapperClientException exception) {
            return new SendMessage(chatId, exception.getMessage());
        }
    }

    private SendMessage processLink(String linkStr, Chat chat) {
        Link link = parseLink(linkStr);
        if (link == null) {
            return new SendMessage(chat.getTelegramId(), LINK_PARSE_ERROR);
        }
        if (chat.getState() == ChatState.TRACK) {
            scrapperService.addLink(chat.getTelegramId(), link);
        } else if (chat.getState() == ChatState.UNTRACK) {
            scrapperService.removeLink(chat.getTelegramId(), link);
        }
        chat.setState(ChatState.DEFAULT);
        scrapperService.update(chat);
        return new SendMessage(chat.getTelegramId(), FINAL_MESSAGE);
    }

    private Link parseLink(String message) {
        try {
            URI link = URI.create(message);
            if (LinkValidator.isValid(link)) {
                return new Link(link);
            }
        } catch (IllegalArgumentException exception) {
            log.warn(exception.getMessage());
        }
        return null;
    }
}
