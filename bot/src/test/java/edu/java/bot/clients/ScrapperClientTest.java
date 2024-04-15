package edu.java.bot.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.bot.BotApplication;
import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.dto.ListLinksResponse;
import edu.java.bot.dto.RemoveLinkRequest;
import java.net.URI;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BotApplication.class)
@ActiveProfiles("test")
public class ScrapperClientTest {
    private final static WireMockServer wireMockServer = new WireMockServer(8080);
    private final static long DEFAULT_CHAT_ID = 123;
    private final static int SUCCESS_STATUS = 200;

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
                .willReturn(ok().withStatus(SUCCESS_STATUS)));
        HttpStatus status = scrapperClient.register(DEFAULT_CHAT_ID);

        assertThat(status).isNull();
    }

    @Test
    public void delete() {
        wireMockServer.stubFor(
            WireMock.delete(urlEqualTo("/tg-chat/" + DEFAULT_CHAT_ID))
                .willReturn(ok().withStatus(SUCCESS_STATUS)));
        HttpStatus status = scrapperClient.delete(DEFAULT_CHAT_ID);

        assertThat(status).isNull();
    }

    @Test
    public void getAll() {
        wireMockServer.stubFor(
            WireMock.get(urlEqualTo("/links"))
                .willReturn(ok().withStatus(SUCCESS_STATUS)));
        ListLinksResponse response = scrapperClient.getAll(DEFAULT_CHAT_ID);

        assertThat(response).isNull();
    }

    @Test
    public void addLink() {
        wireMockServer.stubFor(
            WireMock.post(urlEqualTo("/links"))
                .willReturn(ok().withStatus(SUCCESS_STATUS)));
        HttpStatus status =
            scrapperClient.addLink(DEFAULT_CHAT_ID, new AddLinkRequest(URI.create("http://localhost")));

        assertThat(status).isNull();
    }

    @Test
    public void removeLink() {
        wireMockServer.stubFor(
            WireMock.delete(urlEqualTo("/links"))
                .willReturn(ok().withStatus(SUCCESS_STATUS)));
        HttpStatus status =
            scrapperClient.removeLink(DEFAULT_CHAT_ID, new RemoveLinkRequest(URI.create("http://localhost")));

        assertThat(status).isNull();
    }
}
