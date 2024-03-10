package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repository.ChatLinkRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcChatLinkRepository implements ChatLinkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcChatLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("MultipleStringLiterals")
    public List<Chat> findChatsByLink(Link link) {
        String query = String.format("select c.id, c.status from links l "
            + "join chats_links cl on l.id = cl.link_id "
            + "join chats c on c.id = cl.chat_id "
            + "where cl.link_id = %d", link.getId());
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Chat.class));
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("MultipleStringLiterals")
    public List<Link> findLinksByChat(Chat chat) {
        String query = String.format("select l.id, l.url, l.last_activity from links l "
            + "join chats_links cl on l.id = cl.link_id "
            + "join chats c on c.id = cl.chat_id "
            + "where cl.chat_id = %d", chat.getId());
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Link.class));
    }

    @Override
    @Transactional
    public void add(Chat chat, Link link) {
        jdbcTemplate.update(
            "insert into chats_links(chat_id, link_id) values(?,?)",
            chat.getId(),
            link.getId()
        );
    }

    @Override
    @Transactional
    public void remove(Chat chat, Link link) {
        jdbcTemplate.update(
            "delete from chats_links where chat_id = ? and link_id = ?",
            chat.getId(),
            link.getId()
        );
    }

    @Override
    @Transactional
    public void removeByLink(Link link) {
        jdbcTemplate.update(
            "delete from chats_links where link_id = ?",
            link.getId()
        );
    }

    @Override
    @Transactional
    public void removeByChat(Chat chat) {
        jdbcTemplate.update(
            "delete from chats_links where chat_id = ?",
            chat.getId()
        );
    }
}
