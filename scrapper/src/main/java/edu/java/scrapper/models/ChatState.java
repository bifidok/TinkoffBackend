package edu.java.scrapper.models;

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
