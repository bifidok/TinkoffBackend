package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.dto.LinkUpdateRequest;
import java.net.URI;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class BotClientTest {
    private final static WireMockServer wireMockServer = new WireMockServer(8080);

    @Autowired
    private BotClient botClient;

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
    public void checkUpdate_shouldReturnCorrectValue() {
        configureFor("localhost", 8080);
        wireMockServer.stubFor(
            WireMock.post(urlPathEqualTo("/updates"))
                .willReturn(WireMock.ok()));
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            0,
            URI.create("http://someLink"),
            "something updated",
            null
        );

        HttpStatus status = botClient.checkUpdate(linkUpdateRequest);

        assertThat(status).isNull();
    }

    @Test
    public void checkUpdateWithRetry() {
        configureFor("localhost", 8080);
        wireMockServer.stubFor(
            WireMock.post(urlPathEqualTo("/updates"))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs(STARTED)
                .willReturn(aResponse()
                    .withStatus(502))
                .willSetStateTo("502"));
        wireMockServer.stubFor(
            WireMock.post(urlPathEqualTo("/updates"))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs("502")
                .willReturn(aResponse()
                    .withStatus(HttpStatus.OK.value())));
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            0,
            URI.create("http://someLink"),
            "something updated",
            null
        );

        HttpStatus httpStatus = botClient.checkUpdate(linkUpdateRequest);

        assertThat(httpStatus).isNull();
        wireMockServer.verify(2, postRequestedFor(urlEqualTo("/updates")));
    }
}
