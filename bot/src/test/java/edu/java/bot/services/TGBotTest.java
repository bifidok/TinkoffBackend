package edu.java.bot.services;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.CommandManager;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.enums.UserState;
import edu.java.bot.models.User;
import edu.java.bot.utils.UpdateBuilder;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TGBotTest {
    @InjectMocks
    private TGBot tgBot;
    @Mock
    private StartCommand startCommand;
    @Mock
    private ListCommand listCommand;
    @Mock
    private CommandManager commandManager;
    @Mock
    private UserService userService;
    @Mock
    private TelegramApi api;
    @Captor
    private ArgumentCaptor<String> messageCaptor;

    @Test
    @DisplayName("Input command results in handling it")
    public void process_whenCommand() {
        Update update = UpdateBuilder.createUpdate("/start");
        when(commandManager.findCommandByName(update.message().text())).thenReturn(startCommand);

        int actual = tgBot.process(Collections.singletonList(update));

        assertThat(actual).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
        verify(startCommand, times(1)).handle(any(), anyLong());
        verify(commandManager, times(1)).findCommandByName(anyString());
    }

    @Test
    @DisplayName("Input not command results in handling previous user command")
    public void process_whenNotCommand() {
        Update update = UpdateBuilder.createUpdate("hello");
        User user = new User(0, UserState.TRACK);
        when(userService.findByTelegramId(anyLong())).thenReturn(user);
        when(commandManager.findCommandByName(anyString()))
            .thenReturn(null)
            .thenReturn(listCommand);

        int actual = tgBot.process(Collections.singletonList(update));

        assertThat(actual).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
        verify(listCommand, times(1)).handle(any(), anyLong());
        verify(commandManager, times(2)).findCommandByName(anyString());
    }

    @Test
    @DisplayName("Default message")
    public void process_whenNotCommandAndNoState() {
        Update update = UpdateBuilder.createUpdate("hello");
        User user = new User(0, UserState.DEFAULT);
        when(userService.findByTelegramId(anyLong())).thenReturn(user);
        when(commandManager.findCommandByName(anyString()))
            .thenReturn(null)
            .thenReturn(null);

        int actual = tgBot.process(Collections.singletonList(update));

        verify(api).sendMessage(any(), messageCaptor.capture());
        verify(commandManager, times(2)).findCommandByName(anyString());
        assertThat(actual).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
        assertThat("Not recognized command!").isEqualTo(messageCaptor.getValue());
    }

    @Test
    @DisplayName("Update is null")
    public void process_whenUpdateIsNull() {
        Update update = null;

        int actual = tgBot.process(Collections.singletonList(update));

        assertThat(actual).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
        verify(userService, never()).findByTelegramId(anyLong());
        verify(commandManager, never()).findCommandByName(anyString());
    }

    @Test
    @DisplayName("Update message is empty")
    public void process_whenUpdateMessageIsEmpty() {
        Update update = UpdateBuilder.createUpdate("");

        int actual = tgBot.process(Collections.singletonList(update));

        assertThat(actual).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
        verify(userService, never()).findByTelegramId(anyLong());
        verify(commandManager, never()).findCommandByName(anyString());
    }
}
