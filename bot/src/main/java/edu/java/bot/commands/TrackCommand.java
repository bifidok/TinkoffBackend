package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.enums.UserState;
import edu.java.bot.models.User;
import edu.java.bot.validators.LinkValidator;
import java.net.URI;

public class TrackCommand implements Command {
    private final static String COMMAND_NAME = "/track";
    private final static String COMMAND_DESCRIPTION = "start link tracking";
    private final static String WAIT_FOR_LINK = "Send a link to start tracking it";
    private final static String LINK_PARSE_ERROR = "Cant parse this link. Try another!";
    private final static String SUCCESSFUL_ADD_TO_TRACK = "Ok! I will track it";

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
        Message message = update.message();
        if (message.text().equals(name())) {
            user.setState(UserState.getStateByCommandName(name()));
            return new SendMessage(update.message().chat().id(), WAIT_FOR_LINK);
        }
        URI link = URI.create(message.text());
        if (!LinkValidator.isValid(link)) {
            return new SendMessage(update.message().chat().id(), LINK_PARSE_ERROR);

        }
        //TODO update user track list
        user.resetState();
        return new SendMessage(update.message().chat().id(), SUCCESSFUL_ADD_TO_TRACK);
    }
}
