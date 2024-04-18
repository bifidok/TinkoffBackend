package edu.java.scrapper.bot;

import edu.java.scrapper.dto.LinkUpdateRequest;
import edu.java.scrapper.producers.ScrapperQueueProducer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaBotService implements BotService {
    private final ScrapperQueueProducer scrapperQueueProducer;
    private final String topic;

    @Override
    public void checkUpdate(LinkUpdateRequest linkUpdate) {
        scrapperQueueProducer.sendUpdate(topic, linkUpdate);
    }
}
