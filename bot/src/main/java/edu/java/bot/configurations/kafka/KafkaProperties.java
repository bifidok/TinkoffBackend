package edu.java.bot.configurations.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka", ignoreUnknownFields = false)
public record KafkaProperties(
    @Value("group-id") String groupId,
    @Value("bootstrap-servers") String bootstrapServers,
    @Value("dlq-topic") String dlqTopic
){

}
