package edu.java.bot.configurations.kafka;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.listeners.serializers.LinkUpdateRequestDeserializer;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest> kafkaListenerContainerFactory(
        KafkaProperties kafkaProperties
    ) {
        ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapServers(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LinkUpdateRequestDeserializer.class,
            ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.groupId()
        )));
        return factory;
    }
}
