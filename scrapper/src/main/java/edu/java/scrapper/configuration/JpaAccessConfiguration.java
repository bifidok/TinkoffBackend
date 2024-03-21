package edu.java.scrapper.configuration;

import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.repositories.jpa.JpaGitHubRepositoryRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.repositories.jpa.JpaQuestionRepository;
import edu.java.scrapper.repositories.jpa.impl.JpaChatLinkRepositoryImpl;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.GitHubRepositoryService;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.services.QuestionService;
import edu.java.scrapper.services.jpa.JpaChatService;
import edu.java.scrapper.services.jpa.JpaGitHubRepositoryService;
import edu.java.scrapper.services.jpa.JpaLinkService;
import edu.java.scrapper.services.jpa.JpaQuestionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(
        JpaLinkRepository linkRepository,
        JpaChatRepository chatRepository,
        JpaChatLinkRepositoryImpl chatLinkRepository
    ) {
        return new JpaLinkService(linkRepository, chatRepository, chatLinkRepository);
    }

    @Bean
    public ChatService chatService(
        JpaLinkRepository linkRepository,
        JpaChatRepository chatRepository,
        JpaChatLinkRepositoryImpl chatLinkRepository
    ) {
        return new JpaChatService(chatRepository, linkRepository, chatLinkRepository);
    }

    @Bean
    public GitHubRepositoryService gitHubRepositoryService(
        JpaGitHubRepositoryRepository gitHubRepositoryRepository
    ) {
        return new JpaGitHubRepositoryService(gitHubRepositoryRepository);
    }

    @Bean
    public QuestionService questionService(
        JpaQuestionRepository questionRepository
    ) {
        return new JpaQuestionService(questionRepository);
    }
}
