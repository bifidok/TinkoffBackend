package edu.java.scrapper.models;

import jakarta.persistence.Embeddable;

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
