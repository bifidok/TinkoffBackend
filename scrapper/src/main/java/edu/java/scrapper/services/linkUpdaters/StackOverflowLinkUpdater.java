package edu.java.scrapper.services.linkUpdaters;

import edu.java.scrapper.bot.BotService;
import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.clients.responses.QuestionResponse;
import edu.java.scrapper.dto.LinkUpdateRequest;
import edu.java.scrapper.exceptions.StackOverflowClientException;
import edu.java.scrapper.linkParser.links.StackOverflowLink;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.services.QuestionService;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StackOverflowLinkUpdater {
    private final StackOverflowClient stackOverflowClient;
    private final QuestionService questionService;
    private final ChatService chatService;
    private final LinkService linkService;
    private final BotService botService;

    @Autowired
    public StackOverflowLinkUpdater(
        StackOverflowClient stackOverflowClient,
        QuestionService questionService,
        ChatService chatService,
        LinkService linkService,
        BotService botService
    ) {
        this.stackOverflowClient = stackOverflowClient;
        this.questionService = questionService;
        this.chatService = chatService;
        this.linkService = linkService;
        this.botService = botService;
    }

    public boolean update(StackOverflowLink stackOverflowLink, Link link) {
        try {
            checkByLastActivity(link, stackOverflowLink);
            checkByAnswersInQuestion(link, stackOverflowLink);
            link.setLastCheckTime(OffsetDateTime.now());
            linkService.update(link);
            return true;
        } catch (StackOverflowClientException exception) {
            return false;
        }
    }

    private void checkByLastActivity(Link link, StackOverflowLink stackOverflowLink) throws
        StackOverflowClientException {
        QuestionResponse questionResponse = stackOverflowClient.get(stackOverflowLink.questionId());
        QuestionResponse.ItemResponse response = questionResponse.items().getFirst();
        if (link.getLastActivity().isBefore(response.lastActivity())) {
            List<Long> chatsIds = findLinkChatsIds(link);
            LinkUpdateRequest request =
                new LinkUpdateRequest(link.getId(), link.getUrl(), "Check new updates", chatsIds);
            botService.checkUpdate(request);
        }
        updateLinkDates(link, response.lastActivity());
    }

    private void checkByAnswersInQuestion(Link link, StackOverflowLink stackOverflowLink)
        throws StackOverflowClientException {
        Question question = questionService.findByLink(link);
        if (question == null) {
            Long questionId = Long.parseLong(stackOverflowLink.questionId());
            questionService.add(new Question(questionId, link));
            question = questionService.findByLink(link);
        }
        QuestionResponse questionResponse = stackOverflowClient.get(stackOverflowLink.questionId());
        QuestionResponse.ItemResponse response = questionResponse.items().getFirst();
        if (response.answerCount() > question.getAnswerCount()) {
            LinkUpdateRequest linkUpdateRequest =
                new LinkUpdateRequest(
                    link.getId(),
                    link.getUrl(),
                    createQuestionUpdateMessage(question, response),
                    findLinkChatsIds(link)
                );
            botService.checkUpdate(linkUpdateRequest);
            updateQuestionAnswers(question, response);
        }
    }

    private List<Long> findLinkChatsIds(Link link) {
        List<Chat> chats = chatService.findAll(link.getUrl());
        List<Long> chatsIds = chats.stream().map(Chat::getId).toList();
        return chatsIds;
    }

    private void updateLinkDates(Link link, OffsetDateTime lastActivity) {
        link.setLastActivity(lastActivity);
        linkService.update(link);
    }

    private void updateQuestionAnswers(Question question, QuestionResponse.ItemResponse itemResponse) {
        question.setAnswerCount(itemResponse.answerCount());
        questionService.update(question);
    }

    private String createQuestionUpdateMessage(Question question, QuestionResponse.ItemResponse itemResponse) {
        return String.format("Check %d new answers", itemResponse.answerCount() - question.getAnswerCount());
    }
}
