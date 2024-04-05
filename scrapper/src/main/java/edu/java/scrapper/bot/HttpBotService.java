package edu.java.scrapper.bot;

import edu.java.scrapper.clients.BotClient;
import edu.java.scrapper.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpBotService implements BotService {
    private final BotClient botClient;

    @Override
    public void checkUpdate(LinkUpdateRequest linkUpdate) {
        botClient.checkUpdate(linkUpdate);
    }
}
