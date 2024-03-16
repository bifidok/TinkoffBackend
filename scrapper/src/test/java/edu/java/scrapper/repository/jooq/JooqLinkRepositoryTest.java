package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
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
public class JooqLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqLinkRepository jooqLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnLinks() {
        Link link1 = new Link(URI.create("https://localhost:8080/1"), OffsetDateTime.now());
        Link link2 = new Link(URI.create("https://localhost:8080/2"), OffsetDateTime.now());
        Link link3 = new Link(URI.create("https://localhost:8080/3"), OffsetDateTime.now());
        List<Link> expected = List.of(link1, link2, link3);
        jooqLinkRepository.add(link1);
        jooqLinkRepository.add(link2);
        jooqLinkRepository.add(link3);

        List<Link> actual = jooqLinkRepository.findAll();

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
        jooqLinkRepository.add(link1);
        jooqLinkRepository.add(link2);
        jooqLinkRepository.add(link3);

        Link actual = jooqLinkRepository.findByUrl(link2.getUrl());

        Assertions.assertEquals(link2, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddLink() {
        Link link = new Link(URI.create("https://localhost:8080"), OffsetDateTime.now());

        jooqLinkRepository.add(link);
        List<Link> links = jooqLinkRepository.findAll();

        assertThat(links.contains(link)).isTrue();
    }
    @Test
    @Transactional
    @Rollback
    public void update_shouldChangeLastActivity() {
        OffsetDateTime lastOffsetDateTime = OffsetDateTime.now();
        Link link = new Link(URI.create("https://localhost:8080"),lastOffsetDateTime);
        jooqLinkRepository.add(link);
        link = jooqLinkRepository.findByUrl(link.getUrl());
        OffsetDateTime updatedOffsetDateTime = OffsetDateTime.now();
        link.setLastActivity(updatedOffsetDateTime);

        jooqLinkRepository.update(link);
        Link updatedLink = jooqLinkRepository.findByUrl(link.getUrl());

        assertThat(updatedLink.getLastActivity().getYear()).isEqualTo(link.getLastActivity().getYear());
        assertThat(updatedLink.getLastActivity().getMonth()).isEqualTo(link.getLastActivity().getMonth());
    }

    @Test
    @Transactional
    @Rollback
    public void removeByUrl_shouldRemoveLink() {
        Link link = new Link(URI.create("https://localhost:8080"), OffsetDateTime.now());
        jooqLinkRepository.add(link);

        jooqLinkRepository.remove(link.getUrl());
        List<Link> links = jooqLinkRepository.findAll();

        assertThat(links.contains(link)).isFalse();
    }
}
