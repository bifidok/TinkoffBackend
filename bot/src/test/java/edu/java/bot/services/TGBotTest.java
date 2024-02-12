package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.utility.BotUtils;
import edu.java.bot.commands.CommandManager;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.enums.UserState;
import edu.java.bot.models.User;
import edu.java.bot.repositories.UserRepository;
import java.util.Collections;
import edu.java.bot.utils.UpdateBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
@Component
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
    private UserRepository userRepository;
    @Mock
    private TelegramBot sdkBot;
    @Captor
    private ArgumentCaptor<SendMessage> requestCaptor;

    @Test
    @DisplayName("Input command results in handling it")
    public void process_whenCommand() {
        Update update = UpdateBuilder.createUpdate("/start");
        User user = new User(0, UserState.BASIC);
        when(userRepository.findUserByTelegramId(anyLong())).thenReturn(user);
        MockedStatic<UserState> userState = Mockito.mockStatic(UserState.class);
        userState.when(() -> UserState.getStateByCommandName(anyString())).thenReturn(UserState.BASIC);
        when(commandManager.findCommandByName(update.message().text())).thenReturn(startCommand);

        int actual = tgBot.process(Collections.singletonList(update));

        assertThat(actual).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
        verify(startCommand, times(1)).handle(any(), any());
        verify(commandManager, times(1)).findCommandByName(anyString());
    }

    @Test
    @DisplayName("Input not command results in handling previous user command")
    public void process_whenNotCommand() {
        Update update = UpdateBuilder.createUpdate("hello");
        User user = new User(0, UserState.LIST);
        when(userRepository.findUserByTelegramId(anyLong())).thenReturn(user);
        when(commandManager.findCommandByName(anyString()))
            .thenReturn(null)
            .thenReturn(listCommand);

        int actual = tgBot.process(Collections.singletonList(update));

        assertThat(actual).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
        verify(listCommand, times(1)).handle(any(), any());
        verify(commandManager, times(2)).findCommandByName(anyString());
    }

    @Test
    @DisplayName("Default message")
    public void process_whenNotCommandAndNoState() {
        Update update = UpdateBuilder.createUpdate("hello");
        User user = new User(0, UserState.BASIC);
        when(userRepository.findUserByTelegramId(anyLong())).thenReturn(user);
        when(commandManager.findCommandByName(anyString()))
            .thenReturn(null)
            .thenReturn(null);

        int actual = tgBot.process(Collections.singletonList(update));

        verify(sdkBot).execute(requestCaptor.capture());
        verify(commandManager, times(2)).findCommandByName(anyString());
        assertThat(actual).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
        assertThat("Not recognized command!").isEqualTo(requestCaptor.getValue().getParameters().get("text"));
    }

    @Test
    @DisplayName("Update is null")
    public void process_whenUpdateIsNull() {
        Update update = null;

        int actual = tgBot.process(Collections.singletonList(update));

        assertThat(actual).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
        verify(userRepository, never()).findUserByTelegramId(anyLong());
        verify(commandManager, never()).findCommandByName(anyString());
    }

    @Test
    @DisplayName("Update message is empty")
    public void process_whenUpdateMessageIsEmpty() {
        Update update = UpdateBuilder.createUpdate("");

        int actual = tgBot.process(Collections.singletonList(update));

        assertThat(actual).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
        verify(userRepository, never()).findUserByTelegramId(anyLong());
        verify(commandManager, never()).findCommandByName(anyString());
    }
}
