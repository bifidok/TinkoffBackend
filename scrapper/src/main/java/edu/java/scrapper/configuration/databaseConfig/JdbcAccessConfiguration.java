package edu.java.scrapper.configuration.databaseConfig;

import edu.java.scrapper.repositories.jdbc.JdbcChatLinkRepository;
import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
import edu.java.scrapper.repositories.jdbc.JdbcGitHubRepositoryRepository;
import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repositories.jdbc.JdbcQuestionRepository;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.GitHubRepositoryService;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.services.QuestionService;
import edu.java.scrapper.services.jdbc.JdbcChatService;
import edu.java.scrapper.services.jdbc.JdbcGitHubRepositoryService;
import edu.java.scrapper.services.jdbc.JdbcLinkService;
import edu.java.scrapper.services.jdbc.JdbcQuestionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(
        JdbcLinkRepository linkRepository,
        JdbcChatRepository chatRepository,
        JdbcChatLinkRepository chatLinkRepository
    ) {
        return new JdbcLinkService(linkRepository, chatRepository, chatLinkRepository);
    }

    @Bean
    public ChatService chatService(
        JdbcLinkRepository linkRepository,
        JdbcChatRepository chatRepository,
        JdbcChatLinkRepository chatLinkRepository
    ) {
        return new JdbcChatService(chatRepository, linkRepository, chatLinkRepository);
    }

    @Bean
    public GitHubRepositoryService gitHubRepositoryService(
        JdbcGitHubRepositoryRepository gitHubRepositoryRepository
    ) {
        return new JdbcGitHubRepositoryService(gitHubRepositoryRepository);
    }

    @Bean
    public QuestionService questionService(
        JdbcQuestionRepository questionRepository
    ) {
        return new JdbcQuestionService(questionRepository);
    }
}
