package edu.java.bot.services;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.CommandManager;
import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.metrics.MessagesProcessAmountMetric;
import edu.java.bot.models.Chat;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TGBot implements Bot {
    private final CommandManager commandManager;
    private final TelegramApi api;
    private final ScrapperService userService;
    private final MessagesProcessAmountMetric messagesProcessAmountMetric;

    @Autowired
    public TGBot(CommandManager commandManager, TelegramApi api, ScrapperService userService,
        MessagesProcessAmountMetric messagesProcessAmountMetric
    ) {
        this.commandManager = commandManager;
        this.api = api;
        this.userService = userService;
        this.messagesProcessAmountMetric = messagesProcessAmountMetric;
    }

    @Override
    @PostConstruct
    public void start() {
        api.setUpdatesListener(this);
        setCommands(commandManager.getCommands());
    }

    @Override
    public int process(List<Update> updates) {
        if (updates == null) {
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }
        for (Update update : updates) {
            if (update == null || update.message() == null || update.message().text().isEmpty()) {
                continue;
            }
            Message message = update.message();
            Command inputCommand = commandManager.findCommandByName(message.text());
            if (inputCommand != null) {
                api.execute(inputCommand.handle(message, message.chat().id()));
            } else {
                processText(update);
            }
            messagesProcessAmountMetric.increment();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void updateRequest(LinkUpdateRequest updateRequest) {
        for (Long chatId : updateRequest.getTgChatIds()) {
            String description = String.format("New update in %s \n%s",
                updateRequest.getUrl().toString(),
                updateRequest.getDescription());
            SendMessage message = new SendMessage(chatId, description);
            api.execute(message);
        }
    }

    private void processText(Update update) {
        Chat chat = userService.findChat(update.message().chat().id());
        if (chat == null) {
            return;
        }
        Command lastCommand = commandManager.findCommandByName(chat.getState().getCommandName());
        if (lastCommand != null) {
            api.execute(lastCommand.handle(update.message(), update.message().chat().id()));
        } else {
            api.sendMessage(update, "Not recognized command!");
        }
    }

    @Override
    public void close() {
        api.close();
    }

    private void setCommands(List<Command> commands) {
        if (commands.isEmpty()) {
            log.warn("No commands in bot");
            close();
            return;
        }
        List<BotCommand> botCommands = commands.stream()
            .map(Command::toApiCommand)
            .toList();
        api.execute(new SetMyCommands(botCommands.toArray(BotCommand[]::new)));
    }
}
