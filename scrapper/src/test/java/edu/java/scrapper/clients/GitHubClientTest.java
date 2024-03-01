package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.ScrapperApplication;
import edu.java.clients.GitHubClient;
import edu.java.dto.RepositoryResponse;
import java.time.OffsetDateTime;
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
public class GitHubClientTest {
    private final static WireMockServer wireMockServer = new WireMockServer();

    @Autowired
    private GitHubClient gitHubClient;

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
        OffsetDateTime dateTime = OffsetDateTime.MAX;
        setUpServer("/repos/owner/repo", createBody(id, dateTime));

        RepositoryResponse response = gitHubClient.get("owner", "repo");

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.lastActivity()).isEqualTo(dateTime.toString());
    }

    private String createBody(int id, OffsetDateTime lastActvity) {
        String body = String.format("{\"id\":%d,\"updated_at\":\"%s\"}", id, lastActvity.toString());
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
