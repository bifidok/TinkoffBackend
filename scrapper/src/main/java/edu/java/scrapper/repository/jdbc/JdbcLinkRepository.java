package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class JdbcLinkRepository implements LinkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> findAll() {
        return jdbcTemplate.query("select * from links", new BeanPropertyRowMapper<>(Link.class));
    }

    @Override
    @Transactional
    public void add(Link link) {
        jdbcTemplate.update(
            "insert into links(url) values(?)",
            link.getUrl().toString()
        );
    }

    @Override
    @Transactional
    public void remove(Link link) {
        jdbcTemplate.update(
            "delete from links where id = ?",
            link.getId()
        );
    }
}
