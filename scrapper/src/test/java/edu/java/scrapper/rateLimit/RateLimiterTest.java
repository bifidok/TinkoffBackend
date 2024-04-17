package edu.java.scrapper.rateLimit;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.services.LinkService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ScrapperApplication.class)
@AutoConfigureMockMvc
public class RateLimiterTest extends IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LinkService linkService;

    @Test
    public void getAll_whenEndpointIsRestrictedAfter3RequestsInAShortTime() throws Exception {
        int chatId = 1;
        when(linkService.findAll()).thenReturn(List.of(new Link()));
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(get("/links")
                    .with(request -> {
                        request.setRemoteAddr("192.168.0.2");
                        return request;
                    })
                    .header("tgChatId", chatId))
                .andExpect(status().isOk());
        }

        mockMvc.perform(get("/links")
                .with(request -> {
                    request.setRemoteAddr("192.168.0.2");
                    return request;
                })
                .header("tgChatId", chatId))
            .andExpect(status().isTooManyRequests());
    }
}
