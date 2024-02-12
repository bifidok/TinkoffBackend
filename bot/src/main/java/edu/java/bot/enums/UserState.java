package edu.java.bot.enums;

public enum UserState {
    START,
    HELP,
    TRACK,
    UNTRACK,
    LIST,
    BASIC;
    private final static String START_NAME = "/start";
    private final static String HELP_NAME = "/help";
    private final static String TRACK_NAME = "/track";
    private final static String UNTRACK_NAME = "/untrack";
    private final static String LIST_NAME = "/list";

    public String getCommandName() {
        return switch (this) {
            case START -> START_NAME;
            case HELP -> HELP_NAME;
            case TRACK -> TRACK_NAME;
            case UNTRACK -> UNTRACK_NAME;
            case LIST -> LIST_NAME;
            default -> "";
        };
    }

    public static UserState getStateByCommandName(String commandName) {
        return switch (commandName) {
            case HELP_NAME -> HELP;
            case START_NAME -> START;
            case TRACK_NAME -> TRACK;
            case UNTRACK_NAME -> UNTRACK;
            case LIST_NAME -> LIST;
            default -> BASIC;
        };
    }

}
