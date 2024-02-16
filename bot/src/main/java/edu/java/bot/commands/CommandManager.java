package edu.java.bot.commands;

import edu.java.bot.configurations.ApplicationConfig;
import edu.java.bot.services.UserService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandManager {
    private final UserService userService;
    private final ApplicationConfig applicationConfig;
    private List<Command> commands;

    @Autowired
    public CommandManager(UserService userService, ApplicationConfig applicationConfig) {
        this.userService = userService;
        this.applicationConfig = applicationConfig;
    }

    @PostConstruct
    public void start() {
        Map<String, String> nameToDescription = applicationConfig.commands();
        StartCommand start = new StartCommand(nameToDescription.get("start"), userService);
        TrackCommand track = new TrackCommand(nameToDescription.get("track"), userService);
        UntrackCommand untrack = new UntrackCommand(nameToDescription.get("untrack"), userService);
        ListCommand list = new ListCommand(nameToDescription.get("list"), userService);
        HelpCommand help = new HelpCommand(nameToDescription.get("help"), nameToDescription);
        commands = List.of(
            start,
            track,
            untrack,
            help,
            list
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
