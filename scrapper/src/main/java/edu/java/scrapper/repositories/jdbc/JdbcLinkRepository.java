package edu.java.scrapper.repositories.jdbc;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class JdbcLinkRepository implements LinkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query("select * from links", new BeanPropertyRowMapper<>(Link.class));
    }

    @Override
    public List<Link> findByCheckDateMoreThan(OffsetDateTime dateTime) {
        return jdbcTemplate.query(
            "select * from links where last_check_time < ?",
            new BeanPropertyRowMapper<>(Link.class),
            dateTime.toString()
        );
    }

    @Override
    public Link findByUrl(URI url) {
        return jdbcTemplate.query(
            "select * from links where url = ?",
            new BeanPropertyRowMapper<>(Link.class),
            url.toString()
        ).stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public void add(Link link) {
        jdbcTemplate.update(
            "insert into links(url, last_activity,last_check_time) values(?, ?, ?)",
            link.getUrl().toString(),
            link.getLastActivity(),
            link.getLastCheckTime()
        );
    }

    @Override
    public void update(Link link) {
        jdbcTemplate.update(
            "update links set last_check_time = ?, last_activity = ? where id = ?",
            link.getLastCheckTime(),
            link.getLastActivity(),
            link.getId()
        );
    }

    @Override
    public void remove(URI url) {
        jdbcTemplate.update(
            "delete from links where url = ?",
            url.toString()
        );
    }
}
