package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.clients.responses.RepositoryCommitsResponse;
import edu.java.scrapper.clients.responses.RepositoryResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class GitHubClientTest {
    private final static int PORT = 8080;
    private final static int SUCCESS_STATUS = 200;
    private final static String DEFAULT_OWNER = "owner";
    private final static String DEFAULT_REPO = "repo";
    private final static WireMockServer wireMockServer = new WireMockServer(PORT);

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
    public void getRepoInfo() {
        int id = 0;
        OffsetDateTime dateTime = OffsetDateTime.MAX;
        wireMockServer.stubFor(
            WireMock.get(urlEqualTo("/repos/owner/repo"))
                .willReturn(aResponse()
                    .withStatus(SUCCESS_STATUS)
                    .withHeader("Content-Type", "application/json")
                    .withBody(createRepositoryResponseBody(id, dateTime))));

        RepositoryResponse response = gitHubClient.getRepoInfo(DEFAULT_OWNER, DEFAULT_REPO);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.lastActivity()).isEqualTo(dateTime.toString());
    }

    @Test
    public void getRepoCommitsAfterDateTime() {
        OffsetDateTime dateTime = OffsetDateTime.now();
        String instantFormatDateTime = DateTimeFormatter.ISO_INSTANT.format(dateTime);
        RepositoryCommitsResponse expected = new RepositoryCommitsResponse("1",
            new RepositoryCommitsResponse.Commit("message",
                new RepositoryCommitsResponse.Commit.Author("name", dateTime)));
        wireMockServer.stubFor(
            WireMock.get(urlPathMatching("/repos/owner/repo/commits"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withStatus(SUCCESS_STATUS)
                    .withBody(createRepositoryCommitsResponseBody(expected))));

        List<RepositoryCommitsResponse> responses =
            gitHubClient.getRepoCommitsAfterDateTime(DEFAULT_OWNER, DEFAULT_REPO, instantFormatDateTime);
        RepositoryCommitsResponse actual = responses.get(0);

        assertThat(responses.size() == 1).isTrue();
        assertThat(actual.sha()).isEqualTo(expected.sha());
        assertThat(actual.commit().message()).isEqualTo(expected.commit().message());
        assertThat(actual.commit().author().name()).isEqualTo(expected.commit().author().name());
    }

    private String createRepositoryResponseBody(int id, OffsetDateTime lastActvity) {
        String body = String.format("{\"id\":%d,\"updated_at\":\"%s\"}", id, lastActvity.toString());
        return body;
    }

    private String createRepositoryCommitsResponseBody(RepositoryCommitsResponse response) {
        String body = String.format(
            "[ {\n" +
                "    \"sha\": \"%s\",    \n" +
                "    \"commit\": {\n" +
                "      \"author\": {\n" +
                "        \"name\": \"%s\",\n" +
                "        \"date\": \"%s\"\n" +
                "      },\n" +
                "      \"message\": \"%s\"    \n" +
                "     }\n" +
                "  }\n" +
                "]",
            response.sha(),
            response.commit().author().name(),
            response.commit().author().commitDateTime().toString(),
            response.commit().message()
        );
        return body;
    }

}
