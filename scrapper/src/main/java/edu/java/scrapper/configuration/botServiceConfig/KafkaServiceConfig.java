package edu.java.scrapper.configuration.botServiceConfig;

import edu.java.scrapper.bot.BotService;
import edu.java.scrapper.bot.KafkaBotService;
import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.dto.LinkUpdateRequest;
import edu.java.scrapper.producers.ScrapperQueueProducer;
import edu.java.scrapper.producers.serializers.LinkUpdateRequestSerializer;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class KafkaServiceConfig {
    private final ApplicationConfig applicationConfig;

    @Autowired
    public KafkaServiceConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public BotService botService(ScrapperQueueProducer scrapperQueueProducer) {
        return new KafkaBotService(scrapperQueueProducer, applicationConfig.kafka().scrapperTopic());
    }

    @Bean
    public KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.kafka().bootstrapServers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LinkUpdateRequestSerializer.class
        )));
    }
}
