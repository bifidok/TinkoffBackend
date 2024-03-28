package edu.java.bot.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.bot.BotApplication;
import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.dto.ChatResponse;
import edu.java.bot.dto.ChatUpdateRequest;
import edu.java.bot.dto.ListLinksResponse;
import edu.java.bot.dto.RemoveLinkRequest;
import java.net.URI;
import edu.java.bot.enums.ChatState;
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
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
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
    @AfterEach
    public void afterEach(){
        wireMockServer.resetRequests();
    }

    @Test
    public void register() {
        wireMockServer.stubFor(
            WireMock.post(urlEqualTo("/tg-chat/" + DEFAULT_CHAT_ID))
                .willReturn(ok().withStatus(200)));

        HttpStatus status = scrapperClient.registerChat(DEFAULT_CHAT_ID);

        assertThat(status).isNull();
    }

    @Test
    public void get() {
        wireMockServer.stubFor(
            WireMock.get(urlEqualTo("/tg-chat/" + DEFAULT_CHAT_ID))
                .willReturn(ok().withStatus(200)));

        ChatResponse chatResponse = scrapperClient.getChat(DEFAULT_CHAT_ID);

        assertThat(chatResponse).isNull();
    }

    @Test
    public void getWithRetry() {
        stubFor(
            WireMock.get(urlEqualTo("/tg-chat/" + DEFAULT_CHAT_ID))
                .inScenario("Retry Scenario")
                .willReturn(aResponse()
                    .withStatus(502))
                .willSetStateTo("502"));
        wireMockServer.stubFor(
            WireMock.get(urlEqualTo("/tg-chat/" + DEFAULT_CHAT_ID))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs("502")
                .willReturn(ok().withStatus(200)));

        ChatResponse chatResponse = scrapperClient.getChat(DEFAULT_CHAT_ID);

        assertThat(chatResponse).isNull();
        wireMockServer.verify(2, getRequestedFor(urlEqualTo("/tg-chat/" + DEFAULT_CHAT_ID)));
    }

    @Test
    public void update() {
        wireMockServer.stubFor(
            WireMock.patch(urlEqualTo("/tg-chat"))
                .willReturn(ok().withStatus(200)));

        HttpStatus status =
            scrapperClient.updateChat(new ChatUpdateRequest(DEFAULT_CHAT_ID, ChatState.DEFAULT));

        assertThat(status).isNull();
    }

    @Test
    public void delete() {
        wireMockServer.stubFor(
            WireMock.delete(urlEqualTo("/tg-chat/" + DEFAULT_CHAT_ID))
                .willReturn(ok().withStatus(200)));

        HttpStatus status = scrapperClient.delete(DEFAULT_CHAT_ID);

        assertThat(status).isNull();
    }

    @Test
    public void getAll() {
        wireMockServer.stubFor(
            WireMock.get(urlEqualTo("/links"))
                .willReturn(ok().withStatus(200)));

        ListLinksResponse response = scrapperClient.getAllLinks(DEFAULT_CHAT_ID);

        assertThat(response).isNull();
    }

    @Test
    public void addLink() {
        wireMockServer.stubFor(
            WireMock.post(urlEqualTo("/links"))
                .willReturn(ok().withStatus(200)));

        HttpStatus status =
            scrapperClient.addLink(DEFAULT_CHAT_ID, new AddLinkRequest(URI.create("http://localhost")));

        assertThat(status).isNull();
    }

    @Test
    public void removeLink() {
        wireMockServer.stubFor(
            WireMock.delete(urlEqualTo("/links"))
                .willReturn(ok().withStatus(200)));

        HttpStatus status =
            scrapperClient.removeLink(DEFAULT_CHAT_ID, new RemoveLinkRequest(URI.create("http://localhost")));

        assertThat(status).isNull();
    }
}
