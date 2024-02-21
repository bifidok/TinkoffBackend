package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.enums.UserState;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import edu.java.bot.services.UserService;
import edu.java.bot.validators.LinkValidator;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LinkCommandHandler {
    private final static String WAIT_FOR_LINK = "Send a link";
    private final static String LINK_PARSE_ERROR = "Cant parse this link. Try another!";
    private final static String FINAL_MESSAGE = "Ok!";

    private final UserService userService;

    public LinkCommandHandler(UserService userService) {
        this.userService = userService;
    }

    public SendMessage handle(Message message, User user, UserState userState) {
        if (message.text().equals(userState.getCommandName())) {
            user.setState(userState);
            userService.update(user);
            return new SendMessage(user.getTelegramId(), WAIT_FOR_LINK);
        }
        Link link = parseLink(message.text());
        if (link == null) {
            return new SendMessage(user.getTelegramId(), LINK_PARSE_ERROR);
        }
        if (userState == UserState.TRACK) {
            user.getLinks().add(link);
        } else if (userState == UserState.UNTRACK) {
            user.getLinks().remove(link);
        }
        user.resetState();
        userService.update(user);
        return new SendMessage(user.getTelegramId(), FINAL_MESSAGE);
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
