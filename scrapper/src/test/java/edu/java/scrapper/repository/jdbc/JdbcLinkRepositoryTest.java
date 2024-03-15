package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JdbcLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnLinks() {
        Link link1 = new Link(URI.create("https://localhost:8080/1"), OffsetDateTime.now());
        Link link2 = new Link(URI.create("https://localhost:8080/2"), OffsetDateTime.now());
        Link link3 = new Link(URI.create("https://localhost:8080/3"), OffsetDateTime.now());
        List<Link> expected = List.of(link1, link2, link3);
        jdbcLinkRepository.add(link1);
        jdbcLinkRepository.add(link2);
        jdbcLinkRepository.add(link3);

        List<Link> actual = jdbcLinkRepository.findAll();

        assertThat(expected).isNotNull();
        assertThat(actual).isNotNull();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    @Transactional
    @Rollback
    public void findByUrl_shouldReturnLink() {
        Link link1 = new Link(URI.create("https://localhost:8080/1"), OffsetDateTime.now());
        Link link2 = new Link(URI.create("https://localhost:8080/2"), OffsetDateTime.now());
        Link link3 = new Link(URI.create("https://localhost:8080/3"), OffsetDateTime.now());
        jdbcLinkRepository.add(link1);
        jdbcLinkRepository.add(link2);
        jdbcLinkRepository.add(link3);

        Link actual = jdbcLinkRepository.findByUrl(link2.getUrl());

        Assertions.assertEquals(link2, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddLink() {
        Link link = new Link(URI.create("https://localhost:8080"), OffsetDateTime.now());

        jdbcLinkRepository.add(link);
        List<Link> links = jdbcLinkRepository.findAll();

        assertThat(links.contains(link)).isTrue();
    }
    @Test
    @Transactional
    @Rollback
    public void update_shouldChangeLastActivity() {
        OffsetDateTime lastOffsetDateTime = OffsetDateTime.MIN;
        Link link = new Link(URI.create("https://localhost:8080"),lastOffsetDateTime);
        jdbcLinkRepository.add(link);
        link = jdbcLinkRepository.findByUrl(link.getUrl());
        OffsetDateTime updatedOffsetDateTime = OffsetDateTime.MAX;
        link.setLastActivity(updatedOffsetDateTime);

        jdbcLinkRepository.update(link);
        Link updatedLink = jdbcLinkRepository.findByUrl(link.getUrl());

        assertThat(updatedLink.getLastActivity()).isEqualTo(link.getLastActivity());
    }

    @Test
    @Transactional
    @Rollback
    public void removeByUrl_shouldRemoveLink() {
        Link link = new Link(URI.create("https://localhost:8080"), OffsetDateTime.now());
        jdbcLinkRepository.add(link);

        jdbcLinkRepository.remove(link.getUrl());
        List<Link> links = jdbcLinkRepository.findAll();

        assertThat(links.contains(link)).isFalse();
    }
}
