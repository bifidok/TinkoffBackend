package edu.java.scrapper.repository.test;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
import edu.java.scrapper.repository.ChatRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class aJdbcChatRepositoryTest extends ChatRepositoryTest {
    @Autowired
    public aJdbcChatRepositoryTest(JdbcTemplate jdbcTemplate) {
        super(new JdbcChatRepository(jdbcTemplate));
    }
}
