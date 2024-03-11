package edu.java.bot.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.net.URI;
import edu.java.bot.BotApplication;
import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.dto.ListLinksResponse;
import edu.java.bot.dto.RemoveLinkRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BotApplication.class)
@ActiveProfiles("test")
public class ScrapperClientTest {
    private final static WireMockServer wireMockServer = new WireMockServer(8080);
    private final static long DEFAULT_CHAT_ID = 123;

    @Autowired
    private ScrapperClient scrapperClient;

    @BeforeAll
    public static void setUp() {
        wireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void register() {
        wireMockServer.stubFor(
            WireMock.post(urlEqualTo("/tg-chat/" + DEFAULT_CHAT_ID))
                .willReturn(ok().withStatus(200)));
        HttpStatus status = scrapperClient.register((int) DEFAULT_CHAT_ID);

        assertThat(status).isNull();
    }

    @Test
    public void delete() {
        configureFor("localhost", 8080);
        wireMockServer.stubFor(
            WireMock.delete(urlEqualTo("/tg-chat/" + DEFAULT_CHAT_ID))
                .willReturn(ok().withStatus(200)));
        HttpStatus status = scrapperClient.delete((int) DEFAULT_CHAT_ID);

        assertThat(status).isNull();
    }

    @Test
    public void getAll() {
        configureFor("localhost", 8080);
        wireMockServer.stubFor(
            WireMock.get(urlEqualTo("/links"))
                .willReturn(ok().withStatus(200)));
        ListLinksResponse response = scrapperClient.getAll((int) DEFAULT_CHAT_ID);

        assertThat(response).isNull();
    }

    @Test
    public void addLink() {
        configureFor("localhost", 8080);
        wireMockServer.stubFor(
            WireMock.post(urlEqualTo("/links"))
                .willReturn(ok().withStatus(200)));
        HttpStatus status = scrapperClient.addLink((int) DEFAULT_CHAT_ID, new AddLinkRequest(URI.create("http://localhost")));

        assertThat(status).isNull();
    }

    @Test
    public void removeLink() {
        configureFor("localhost", 8080);
        wireMockServer.stubFor(
            WireMock.delete(urlEqualTo("/links"))
                .willReturn(ok().withStatus(200)));
        HttpStatus status = scrapperClient.removeLink((int) DEFAULT_CHAT_ID, new RemoveLinkRequest(URI.create("http://localhost")));

        assertThat(status).isNull();
    }

}
