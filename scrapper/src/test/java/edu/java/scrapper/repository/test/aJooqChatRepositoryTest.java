package edu.java.scrapper.repository.test;

import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.repositories.jooq.JooqChatRepository;
import edu.java.scrapper.repository.ChatRepositoryTest;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class aJooqChatRepositoryTest extends ChatRepositoryTest {
    @Autowired
    public aJooqChatRepositoryTest(DSLContext dslContext) {
        super(new JooqChatRepository(dslContext));
    }
}
