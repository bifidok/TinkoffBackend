package edu.java.scrapper.producers;

import edu.java.scrapper.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScrapperQueueProducer {
    private final KafkaTemplate<String, LinkUpdateRequest> template;

    public void sendUpdate(String topic, LinkUpdateRequest linkUpdateRequest) {
        template.send(topic, linkUpdateRequest);
    }
}
