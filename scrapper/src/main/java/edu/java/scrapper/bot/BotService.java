package edu.java.scrapper.bot;

import edu.java.scrapper.dto.LinkUpdateRequest;

public interface BotService {
    void checkUpdate(LinkUpdateRequest linkUpdate);
}
