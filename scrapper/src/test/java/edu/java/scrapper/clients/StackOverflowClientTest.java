package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import edu.java.clients.StackOverflowClient;
import edu.java.dto.QuestionResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StackOverflowClientTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();
    @Autowired
    private StackOverflowClient stackOverflowClient;

    @Test
    public void get() {
        int id = 0;
        LocalDateTime dateTime = LocalDateTime.MIN;
        configure(id, dateTime);

        QuestionResponse response = stackOverflowClient.get(String.valueOf(id));
        QuestionResponse.ItemResponse item = response.items().get(0);

        assertThat(item.id()).isEqualTo(0);
        assertThat(item.lastActivity()).isEqualTo(dateTime.atOffset(ZoneOffset.UTC));
    }

    private void configure(int id, LocalDateTime lastActvity) {
        String body = String.format(
            "{\"items\":[{\"last_activity_date\":%d,\"question_id\":%d}]}\n",
            lastActvity.toEpochSecond(ZoneOffset.UTC), id
        );
        configureFor("localhost", 8080);
        stubFor(WireMock.get(urlEqualTo(String.format("/questions/%d?site=stackoverflow", id)))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", "application/json")
                .withBody(body)));
    }
}
