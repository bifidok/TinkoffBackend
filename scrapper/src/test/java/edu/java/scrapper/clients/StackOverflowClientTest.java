package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.clients.responses.QuestionResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class StackOverflowClientTest {
    private final static int SUCCESS_STATUS = HttpStatus.OK.value();
    private final static int BAD_GATEWAY_STATUS = HttpStatus.BAD_GATEWAY.value();
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

    @AfterEach
    public void afterEach() {
        wireMockServer.resetRequests();
    }

    @Test
    public void get() {
        int id = 0;
        LocalDateTime dateTime = LocalDateTime.MIN;
        wireMockServer.stubFor(
            WireMock.get(urlEqualTo(String.format("/questions/%d?site=stackoverflow", id)))
                .willReturn(aResponse()
                    .withStatus(SUCCESS_STATUS)
                    .withHeader("Content-Type", "application/json")
                    .withBody(createBody(id, dateTime))));

        QuestionResponse response = stackOverflowClient.get(String.valueOf(id));
        QuestionResponse.ItemResponse item = response.items().get(0);

        assertThat(item.id()).isEqualTo(id);
        assertThat(item.lastActivity()).isEqualTo(dateTime.atOffset(ZoneOffset.UTC));
    }

    @Test
    public void getWithRetry() {
        int id = 0;
        LocalDateTime dateTime = LocalDateTime.MIN;
        wireMockServer.stubFor(
            WireMock.get(urlEqualTo(String.format("/questions/%d?site=stackoverflow", id)))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs(STARTED)
                .willReturn(aResponse()
                    .withStatus(BAD_GATEWAY_STATUS))
                .willSetStateTo(String.valueOf(BAD_GATEWAY_STATUS)));
        wireMockServer.stubFor(
            WireMock.get(urlEqualTo(String.format("/questions/%d?site=stackoverflow", id)))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs(String.valueOf(BAD_GATEWAY_STATUS))
                .willReturn(aResponse()
                    .withStatus(SUCCESS_STATUS)
                    .withHeader("Content-Type", "application/json")
                    .withBody(createBody(id, dateTime))));

        QuestionResponse response = stackOverflowClient.get(String.valueOf(id));
        QuestionResponse.ItemResponse item = response.items().get(0);

        assertThat(item.id()).isEqualTo(id);
        assertThat(item.lastActivity()).isEqualTo(dateTime.atOffset(ZoneOffset.UTC));
        wireMockServer.verify(2, getRequestedFor(urlEqualTo(String.format("/questions/%d?site=stackoverflow", id))));
    }

    private String createBody(int id, LocalDateTime lastActvity) {
        String body = String.format(
            "{\"items\":[{\"last_activity_date\":%d,\"question_id\":%d}]}\n",
            lastActvity.toEpochSecond(ZoneOffset.UTC), id
        );
        return body;
    }
}
