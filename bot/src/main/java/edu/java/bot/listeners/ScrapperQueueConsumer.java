package edu.java.bot.listeners;

import edu.java.bot.configurations.kafka.KafkaProperties;
import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.services.TGBot;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScrapperQueueConsumer {
    private final TGBot tgBot;
    private final Validator validator;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    @KafkaListener(topics = "${app.scrapper-topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listenStringMessages(
        @Payload LinkUpdateRequest message,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition
    ) {
        log.info("Received Message from partition {}: {}", partition, message);
        if (isValidMessage(message)) {
            tgBot.updateRequest(message);
        } else {
            log.warn("Message ", message, " is not valid");
            handleDltLinkUpdateRequest(message);
        }
    }

    private boolean isValidMessage(LinkUpdateRequest linkUpdateRequest) {
        return validator.validate(linkUpdateRequest).isEmpty();
    }

    @DltHandler
    private void handleDltLinkUpdateRequest(
        @Payload LinkUpdateRequest linkUpdateRequest
    ) {
        log.info("Event on dlt, payload={}", linkUpdateRequest);
        kafkaTemplate.send(kafkaProperties.dlqTopic(), linkUpdateRequest);
    }
}
