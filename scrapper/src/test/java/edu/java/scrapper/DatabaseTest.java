package edu.java.scrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
public class DatabaseTest extends IntegrationTest {

    @Test
    public void checkChatsTableColumns_shouldContainTwoColumns() {
        try (Connection connection = POSTGRES.createConnection("");) {
            PreparedStatement statement = connection.prepareStatement("select * from chats");

            ResultSet resultSet = statement.executeQuery();

            assertThat(resultSet.getMetaData().getColumnName(1)).isEqualTo("id");
            assertThat(resultSet.getMetaData().getColumnName(2)).isEqualTo("status");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkLinksTableColumns_shouldContainThreeColumns() {
        try (Connection connection = POSTGRES.createConnection("");) {
            PreparedStatement statement = connection.prepareStatement("select * from links");

            ResultSet resultSet = statement.executeQuery();

            assertThat(resultSet.getMetaData().getColumnName(1)).isEqualTo("id");
            assertThat(resultSet.getMetaData().getColumnName(2)).isEqualTo("url");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkChatsLinksTableColumns_shouldContainTwoColumns() {
        try (Connection connection = POSTGRES.createConnection("");) {
            PreparedStatement statement = connection.prepareStatement("select * from chats_links");

            ResultSet resultSet = statement.executeQuery();

            assertThat(resultSet.getMetaData().getColumnName(1)).isEqualTo("chat_id");
            assertThat(resultSet.getMetaData().getColumnName(2)).isEqualTo("link_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
