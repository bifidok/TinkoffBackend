package edu.java.bot.commands;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CommandManager {
    private List<Command> commands;

    @PostConstruct
    public void start() {
        commands = List.of(
            new StartCommand(),
            new HelpCommand(),
            new ListCommand(),
            new TrackCommand(),
            new UntrackCommand()
        );
    }

    public List<Command> getCommands() {
        return commands;
    }

    public Command findCommandByName(String name) {
        for (Command command : commands) {
            if (command.name().equals(name)) {
                return command;
            }
        }
        return null;
    }
}
