package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.repository.ChatRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class JdbcChatRepository implements ChatRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> findAll() {
        return jdbcTemplate.query("select * from chats", new BeanPropertyRowMapper<>(Chat.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Chat findById(long tgChatId) {
        String query = String.format("select * from chats where id = %d", tgChatId);
        try {
            return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Chat.class));
        } catch (DataAccessException exception) {
            log.info(exception.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public void add(Chat chat) {
        jdbcTemplate.update(
            "insert into chats(id,status) values(?,?::state)",
            chat.getId(),
            chat.getStatus().toString()
        );
    }

    @Override
    @Transactional
    public void remove(Chat chat) {
        jdbcTemplate.update(
            "delete from chats where id = ?",
            chat.getId()
        );
    }
}