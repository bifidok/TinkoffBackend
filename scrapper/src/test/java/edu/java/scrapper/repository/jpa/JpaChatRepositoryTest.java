package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Chat;
import java.util.List;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.repositories.ChatRepository;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.repository.ChatRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JpaChatRepositoryTest extends ChatRepositoryTest {

    @Autowired
    public JpaChatRepositoryTest(JpaChatRepository chatRepository) {
        super(chatRepository);
    }
}
