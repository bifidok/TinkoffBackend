package edu.java.bot.listeners.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.dto.LinkUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class LinkUpdateRequestSerializer implements Serializer<LinkUpdateRequest> {
    @Override
    public byte[] serialize(String s, LinkUpdateRequest o) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(o).getBytes();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }
}
