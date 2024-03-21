package edu.java.scrapper.configuration;

import edu.java.scrapper.repositories.jooq.JooqChatLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqChatRepository;
import edu.java.scrapper.repositories.jooq.JooqGitHubRepositoryRepository;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import edu.java.scrapper.repositories.jooq.JooqQuestionRepository;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.GitHubRepositoryService;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.services.QuestionService;
import edu.java.scrapper.services.jooq.JooqChatService;
import edu.java.scrapper.services.jooq.JooqGitHubRepositoryService;
import edu.java.scrapper.services.jooq.JooqLinkService;
import edu.java.scrapper.services.jooq.JooqQuestionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public LinkService linkService(
        JooqLinkRepository linkRepository,
        JooqChatRepository chatRepository,
        JooqChatLinkRepository chatLinkRepository
    ) {
        return new JooqLinkService(linkRepository, chatRepository, chatLinkRepository);
    }

    @Bean
    public ChatService chatService(
        JooqLinkRepository linkRepository,
        JooqChatRepository chatRepository,
        JooqChatLinkRepository chatLinkRepository
    ) {
        return new JooqChatService(chatRepository, linkRepository, chatLinkRepository);
    }

    @Bean
    public GitHubRepositoryService gitHubRepositoryService(
        JooqGitHubRepositoryRepository gitHubRepositoryRepository
    ) {
        return new JooqGitHubRepositoryService(gitHubRepositoryRepository);
    }

    @Bean
    public QuestionService questionService(
        JooqQuestionRepository questionRepository
    ) {
        return new JooqQuestionService(questionRepository);
    }
}
