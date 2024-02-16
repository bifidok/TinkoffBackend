package edu.java.bot.services;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import java.util.List;

public interface Bot extends AutoCloseable, UpdatesListener {
    @Override
    int process(List<Update> updates);

    void start();

    @Override
    void close();
}
