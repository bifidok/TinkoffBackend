package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import edu.java.scrapper.dto.RepositoryResponse;
import java.time.OffsetDateTime;
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
public class GitHubClientTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();
    @Autowired
    private GitHubClient gitHubClient;

    @Test
    public void get() {
        configure(0, OffsetDateTime.MAX);

        RepositoryResponse response = gitHubClient.get("owner", "repo");

        assertThat(response.id()).isEqualTo(0);
        assertThat(response.lastActivity()).isEqualTo(OffsetDateTime.MAX.toString());
    }

    private void configure(int id, OffsetDateTime lastActvity) {
        String body = String.format("{\"id\":%d,\"updated_at\":\"%s\"}", id, lastActvity.toString());
        configureFor("localhost", 8080);
        stubFor(WireMock.get(urlEqualTo("/repos/owner/repo"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", "application/json")
                .withBody(body)));
    }
}
