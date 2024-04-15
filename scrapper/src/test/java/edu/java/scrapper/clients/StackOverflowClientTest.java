package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.dto.QuestionResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class StackOverflowClientTest {
    private final static WireMockServer wireMockServer = new WireMockServer();

    @Autowired
    private StackOverflowClient stackOverflowClient;

    @BeforeAll
    public static void setUp() {
        wireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void get() {
        int id = 0;
        LocalDateTime dateTime = LocalDateTime.MIN;
        setUpServer(String.format("/questions/%d?site=stackoverflow", id), createBody(id, dateTime));

        QuestionResponse response = stackOverflowClient.get(String.valueOf(id));
        QuestionResponse.ItemResponse item = response.items().get(0);

        assertThat(item.id()).isEqualTo(id);
        assertThat(item.lastActivity()).isEqualTo(dateTime.atOffset(ZoneOffset.UTC));
    }

    private String createBody(int id, LocalDateTime lastActvity) {
        String body = String.format(
            "{\"items\":[{\"last_activity_date\":%d,\"question_id\":%d}]}\n",
            lastActvity.toEpochSecond(ZoneOffset.UTC), id
        );
        return body;
    }

    private void setUpServer(String url, String body) {
        configureFor("localhost", 8080);
        wireMockServer.stubFor(
            WireMock.get(urlEqualTo(url))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(body)));
    }
}
