package edu.java.bot.listeners;

import edu.java.bot.BotApplication;
import edu.java.bot.configurations.kafka.KafkaProperties;
import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.services.TGBot;
import jakarta.validation.Validator;
import java.net.URI;
import java.util.Collections;
import java.util.Set;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BotApplication.class)
@ActiveProfiles("test")
@DirtiesContext
public class ScrapperQueueConsumerTest {
    @InjectMocks
    private ScrapperQueueConsumer scrapperQueueConsumer;
    @Mock
    private KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    @Mock
    private TGBot bot;
    @Mock
    private Validator validator;
    @Mock
    private KafkaProperties properties;

    @Test
    public void listenStringMessages_whenValidMessage() {
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            1,
            URI.create("http://test"),
            "description",
            Collections.singletonList(123L)
        );

        scrapperQueueConsumer.listenStringMessages(linkUpdateRequest, 0);

        verify(bot, times(1)).updateRequest(any());
    }

    @Test
    public void listenStringMessages_whenInvalidMessage() {
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            1,
            null,
            "description",
            Collections.emptyList()
        );
        when(validator.validate(linkUpdateRequest)).thenReturn(Set.of(ConstraintViolationImpl.forParameterValidation(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )));
        scrapperQueueConsumer.listenStringMessages(linkUpdateRequest, 0);

        verify(bot, never()).updateRequest(any());
        verify(kafkaTemplate, times(1)).send(any(), any());
    }
}
