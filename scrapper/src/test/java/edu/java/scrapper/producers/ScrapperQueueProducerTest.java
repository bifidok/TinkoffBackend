package edu.java.scrapper.producers;

import edu.java.scrapper.IntegrationKafkaTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.dto.LinkUpdateRequest;
import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
@DirtiesContext
public class ScrapperQueueProducerTest extends IntegrationKafkaTest {
    private final static String DEFAULT_TOPIC = "topic";

    @Autowired
    private ScrapperQueueProducer scrapperQueueProducer;
    private KafkaConsumer consumer;

    @BeforeEach
    public void initEach() {
        consumer = new KafkaConsumer(getConsumerProps());
        consumer.subscribe(Collections.singletonList(DEFAULT_TOPIC));
    }

    @Test
    public void sendUpdate() {
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            1,
            URI.create("http://test"),
            "description",
            Collections.emptyList()
        );
        scrapperQueueProducer.sendUpdate(DEFAULT_TOPIC, linkUpdateRequest);
        ConsumerRecords<String, LinkUpdateRequest> record = consumer.poll(Duration.ofSeconds(10));
        LinkUpdateRequest linkUpdateRequestFromConsumer = record.iterator().next().value();

        assertEquals(linkUpdateRequestFromConsumer, linkUpdateRequest);
        consumer.close();
    }

    private Map<String, Object> getConsumerProps() {
        Map<String, Object> props =
            KafkaTestUtils.consumerProps(KAFKA.getBootstrapServers(), "test-group", "false");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            JsonDeserializer.class
        );
        props.put(
            JsonDeserializer.VALUE_DEFAULT_TYPE,
            LinkUpdateRequest.class
        );
        return props;
    }
}
