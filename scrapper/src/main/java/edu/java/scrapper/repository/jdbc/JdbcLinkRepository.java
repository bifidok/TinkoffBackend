package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
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
    @Transactional(readOnly = true)
    public Link findByUrl(URI url) {
        String query = String.format("select * from links where url = '%s'", url.toString());
        try {
            return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Link.class));
        } catch (DataAccessException exception) {
            log.info(exception.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public void add(Link link) {
        jdbcTemplate.update(
            "insert into links(url, last_activity) values(?, ?)",
            link.getUrl().toString(),
            link.getLastActivity()
        );
    }

    @Override
    @Transactional
    public void remove(URI url) {
        jdbcTemplate.update(
            "delete from links where url = ?",
            url.toString()
        );
    }
}
