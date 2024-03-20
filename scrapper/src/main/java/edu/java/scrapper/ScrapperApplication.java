package edu.java.scrapper;

import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class ScrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository) {
        return (args) -> {
//            Chat chat = new Chat(123L,ChatState.DEFAULT);
//            Link link = new Link(URI.create("http://sfda"));
//            link.getChats().add(chat);
//            chat.getLinks().add(link);
//            jpaChatRepository.save(chat.getId(),chat.getStatus().toString());
//            jpaLinkRepository.save(link.getUrl().toString(),link.getLastActivity(),link.getLastCheckTime());
//            List<Link> links = jpaLinkRepository.findAll();
//            for(Link link2 : links){
//                System.out.println(link2.getChats());
//            }
        };
    }
}
