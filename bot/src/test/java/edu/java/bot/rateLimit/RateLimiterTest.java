package edu.java.bot.rateLimit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.java.bot.BotApplication;
import edu.java.bot.dto.LinkUpdateRequest;
import java.net.URI;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BotApplication.class)
@AutoConfigureMockMvc
public class RateLimiterTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkUpdate_whenEndpointIsRestrictedAfter3RequestsInAShortTime() throws Exception {
        LinkUpdateRequest updateRequest =
            new LinkUpdateRequest(1, URI.create("http://localhost"), "", Collections.singletonList(123L));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(updateRequest);

        for (int i = 0; i < 10; i++) {
            mockMvc.perform(post("/updates")
                    .with(request -> {
                        request.setRemoteAddr("192.168.0.2");
                        return request;
                    })
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andExpect(status().isOk());
        }

        mockMvc.perform(post("/updates")
                .with(request -> {
                    request.setRemoteAddr("192.168.0.2");
                    return request;
                })
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isTooManyRequests());
    }
}
