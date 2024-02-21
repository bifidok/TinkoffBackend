package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configurations.ApplicationConfig;
import edu.java.bot.exceptionHandlers.BotExceptionHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelegramApi {
    private final TelegramBot telegramBot;

    @Autowired
    public TelegramApi(ApplicationConfig config) {
        telegramBot = new TelegramBot(config.telegramToken());
    }

    @PostConstruct
    public void start() {

    }

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    public void sendMessage(Update update, String message) {
        execute(new SendMessage(update.message().chat().id(), message));
    }

    public void setUpdatesListener(UpdatesListener listener) {
        telegramBot.setUpdatesListener(listener, new BotExceptionHandler());
    }

    public void close() {
        telegramBot.shutdown();
    }
}
