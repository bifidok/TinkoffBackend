package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import edu.java.scrapper.repositories.LinkRepository;
import edu.java.scrapper.repositories.QuestionRepository;
import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repositories.jdbc.JdbcQuestionRepository;
import edu.java.scrapper.repository.QuestionRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JdbcQuestionRepositoryTest extends QuestionRepositoryTest {

    @Autowired
    public JdbcQuestionRepositoryTest(JdbcQuestionRepository questionRepository, JdbcLinkRepository linkRepository) {
        super(questionRepository, linkRepository);
    }
}
