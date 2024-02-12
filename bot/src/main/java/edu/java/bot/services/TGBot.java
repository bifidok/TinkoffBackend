package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.CommandManager;
import edu.java.bot.configurations.ApplicationConfig;
import edu.java.bot.models.User;
import edu.java.bot.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TGBot implements Bot {
    private final static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private CommandManager commandManager;
    //@Autowired
    private UserRepository userRepository;
    private TelegramBot bot;

    @Override
    @PostConstruct
    public void start() {
        bot = new TelegramBot(applicationConfig.telegramToken());
        setUpdateListener();
        setCommands(commandManager.getCommands());
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        Update update = updates.get(updates.size() - 1);
        if (update != null && update.message() != null && !update.message().text().isEmpty()) {
            User user = userRepository.findUserByTelegramId(update.message().chat().id());
            Command inputCommand = commandManager.findCommandByName(update.message().text());
            if (inputCommand != null) {
                bot.execute(inputCommand.handle(update, user));
            } else {
                Command lastCommand = commandManager.findCommandByName(user.getState().getCommandName());
                if (lastCommand != null) {
                    bot.execute(lastCommand.handle(update, user));
                } else {
                    sendMessage(update, "Not recognized command!");
                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        bot.shutdown();
    }

    private void sendMessage(Update update, String message) {
        bot.execute(new SendMessage(update.message().chat().id(), message));
    }

    private void setCommands(List<Command> commands) {
        if (commands.isEmpty()) {
            return;
        }
        List<BotCommand> botCommands = commands.stream()
            .map(Command::toApiCommand)
            .toList();
        this.execute(new SetMyCommands(botCommands.toArray(new BotCommand[0])));
    }

    private void setUpdateListener() {
        bot.setUpdatesListener(this, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            } else {
                LOGGER.warn(e.getMessage());
            }
        });
    }
}
