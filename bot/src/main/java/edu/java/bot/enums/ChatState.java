package edu.java.bot.enums;

/**
 * User chat state in commands where we need to track the stage
 */
public enum ChatState {
    DEFAULT(""),
    TRACK("/track"),
    UNTRACK("/untrack");
    private final String commandName;

    ChatState(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
