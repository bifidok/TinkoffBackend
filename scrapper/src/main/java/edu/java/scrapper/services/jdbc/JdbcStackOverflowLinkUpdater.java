package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.clients.BotClient;
import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.clients.responses.QuestionResponse;
import edu.java.scrapper.dto.LinkUpdateRequest;
import edu.java.scrapper.linkParser.links.StackOverflowLink;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JdbcStackOverflowLinkUpdater {
    private final StackOverflowClient stackOverflowClient;
    private final ChatService chatService;
    private final LinkService linkService;
    private final BotClient botClient;

    @Autowired
    public JdbcStackOverflowLinkUpdater(
        StackOverflowClient stackOverflowClient,
        ChatService chatService,
        LinkService linkService,
        BotClient botClient
    ) {
        this.stackOverflowClient = stackOverflowClient;
        this.chatService = chatService;
        this.linkService = linkService;
        this.botClient = botClient;
    }

    public void update(StackOverflowLink stackOverflowLink, Link link) {
        QuestionResponse questionResponse = stackOverflowClient.get(stackOverflowLink.questionId());
        QuestionResponse.ItemResponse response = questionResponse.items().getFirst();
        checkByLastActivity(link, response);
        updateLinkDates(link, response.lastActivity());
    }

    private void checkByLastActivity(Link link, QuestionResponse.ItemResponse response) {
        if (link.getLastActivity().isBefore(response.lastActivity())) {
            List<Chat> chats = chatService.findAll(link.getUrl());
            List<Long> chatsIds = chats.stream().map(Chat::getId).toList();
            LinkUpdateRequest request =
                new LinkUpdateRequest(link.getId(), link.getUrl(), "Check new updates", chatsIds);
            botClient.checkUpdate(request);
        }
    }

    private void updateLinkDates(Link link, OffsetDateTime lastActivity) {
        link.setLastCheckTime(OffsetDateTime.now());
        link.setLastActivity(lastActivity);
        linkService.update(link);
    }
}
