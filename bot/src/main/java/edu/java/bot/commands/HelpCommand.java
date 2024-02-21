package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Map;

public class HelpCommand implements Command {
    private final static String COMMAND_NAME = "/help";

    private final String commandDescription;
    private final String helpText;

    public HelpCommand(String description, Map<String, String> commands) {
        commandDescription = description;
        helpText = createHelpText(commands);
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
        return new SendMessage(userId, helpText);
    }

    private String createHelpText(Map<String, String> commands) {
        StringBuilder builder = new StringBuilder();
        for (var entry : commands.entrySet()) {
            builder.append(entry.getKey())
                .append(" - ")
                .append(entry.getValue())
                .append("\n");
        }
        return builder.toString();
    }
}
