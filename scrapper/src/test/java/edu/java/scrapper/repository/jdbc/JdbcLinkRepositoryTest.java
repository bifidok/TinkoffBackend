package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Link;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
public class JdbcLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnLinks() {
        List<Link> expected = null;
        try (Connection connection = POSTGRES.createConnection("")) {
            PreparedStatement statement = connection.prepareStatement("select * from links");

            ResultSet resultSet = statement.executeQuery();
            expected = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                URI uri = URI.create(resultSet.getString("url"));
                Link link = new Link(id, uri);
                expected.add(link);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Link> actual = jdbcLinkRepository.findAll();

        assertThat(expected).isNotNull();
        assertThat(actual).isNotNull();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddLink() {
        Link link = new Link(1, URI.create("https://localhost:8080"));

        jdbcLinkRepository.add(link);
        List<Link> links = jdbcLinkRepository.findAll();

        assertThat(links.contains(link)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void remove_shouldRemoveLink() {
        Link link = new Link(1, URI.create("https://localhost:8080"));
        jdbcLinkRepository.add(link);

        jdbcLinkRepository.remove(link);
        List<Link> links = jdbcLinkRepository.findAll();

        assertThat(links.contains(link)).isFalse();
    }
}
