package edu.java.bot.listeners.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.dto.LinkUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

@Slf4j
public class LinkUpdateRequestDeserializer implements Deserializer<LinkUpdateRequest> {

    @Override
    public LinkUpdateRequest deserialize(String s, byte[] bytes) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(bytes, LinkUpdateRequest.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }
}
