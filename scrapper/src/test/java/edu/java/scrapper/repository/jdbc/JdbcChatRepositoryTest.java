package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.models.Link;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
public class JdbcChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnChats() {
        List<Chat> expected = null;
        try (Connection connection = POSTGRES.createConnection("")) {
            PreparedStatement statement = connection.prepareStatement("select * from chats");

            ResultSet resultSet = statement.executeQuery();
            expected = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                ChatState status = ChatState.valueOf(resultSet.getString("status"));
                Chat chat = new Chat(id,status);
                expected.add(chat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Chat> actual = jdbcChatRepository.findAll();

        assertThat(expected).isNotNull();
        assertThat(actual).isNotNull();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddChat() {
        Chat chat = new Chat(2,ChatState.TRACK);

        jdbcChatRepository.add(chat);
        List<Chat> list = jdbcChatRepository.findAll();

        assertThat(list.contains(chat)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveChat() {
        Chat chat = new Chat(2,ChatState.TRACK);
        jdbcChatRepository.add(chat);

        jdbcChatRepository.remove(chat);
        List<Chat> list = jdbcChatRepository.findAll();

        assertThat(list.contains(chat)).isFalse();
    }
}
