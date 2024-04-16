package edu.java.scrapper.repositories.jdbc;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.ChatLinkRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("jdbcChatLinkRepository")
public class JdbcChatLinkRepository implements ChatLinkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcChatLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public List<Chat> findChatsByLink(Link link) {
        return jdbcTemplate.query(
            "select c.id, c.status from links l "
                + "join chats_links cl on l.id = cl.link_id "
                + "join chats c on c.id = cl.chat_id "
                + "where cl.link_id = ?",
            new BeanPropertyRowMapper<>(Chat.class),
            link.getId()
        );
    }

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public List<Link> findLinksByChat(Chat chat) {
        return jdbcTemplate.query("select l.id, l.url, l.last_activity, l.last_check_time from links l "
                + "join chats_links cl on l.id = cl.link_id "
                + "join chats c on c.id = cl.chat_id "
                + "where cl.chat_id = ?",
            new BeanPropertyRowMapper<>(Link.class),
            chat.getId()
        );
    }

    @Override
    public void add(Chat chat, Link link) {
        jdbcTemplate.update(
            "insert into chats_links(chat_id, link_id) values(?,?)",
            chat.getId(),
            link.getId()
        );
    }

    @Override
    public void remove(Chat chat, Link link) {
        jdbcTemplate.update(
            "delete from chats_links where chat_id = ? and link_id = ?",
            chat.getId(),
            link.getId()
        );
    }

    @Override
    public void removeUnusedLinks() {
        jdbcTemplate.update("delete from links where id not in (select link_id from chats_links)");
    }
}
