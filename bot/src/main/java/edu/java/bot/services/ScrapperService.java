package edu.java.bot.services;

import edu.java.bot.clients.ScrapperClient;
import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.dto.ChatResponse;
import edu.java.bot.dto.ChatUpdateRequest;
import edu.java.bot.dto.ListLinksResponse;
import edu.java.bot.dto.RemoveLinkRequest;
import edu.java.bot.exceptions.ScrapperClientException;
import edu.java.bot.models.Chat;
import edu.java.bot.models.Link;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScrapperService {
    private final ScrapperClient scrapperClient;

    @Autowired
    public ScrapperService(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    public Chat findChat(long tgChatId) throws ScrapperClientException {
        ChatResponse chatResponse = scrapperClient.getChat(tgChatId);
        if (chatResponse != null) {
            return new Chat(chatResponse.getId(), chatResponse.getState());
        }
        return null;
    }

    public void register(long tgChatId) throws ScrapperClientException {
        scrapperClient.registerChat(tgChatId);
    }

    public void delete(long tgChatId) throws ScrapperClientException {
        scrapperClient.delete(tgChatId);
    }

    public void update(Chat chat) throws ScrapperClientException {
        scrapperClient.updateChat(new ChatUpdateRequest(chat.getTelegramId(), chat.getState()));
    }

    public List<Link> getAll(long tgChatId) throws ScrapperClientException {
        ListLinksResponse listLinksResponse = scrapperClient.getAllLinks(tgChatId);
        return listLinksResponse.getLinks().stream()
            .map(linkResponse -> new Link(linkResponse.getUri()))
            .toList();
    }

    public void addLink(long tgChatId, Link link) throws ScrapperClientException {
        scrapperClient.addLink(tgChatId, new AddLinkRequest(link.uri()));
    }

    public void removeLink(long tgChatId, Link link) throws ScrapperClientException {
        scrapperClient.removeLink(tgChatId, new RemoveLinkRequest(link.uri()));
    }
}
