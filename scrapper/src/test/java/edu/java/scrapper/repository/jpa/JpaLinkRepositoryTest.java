package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ScrapperApplication.class)
@ActiveProfiles("test")
public class JpaLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaLinkRepository jpaLinkRepository;
    private Link baseLink;

    @BeforeEach
    public void initEach() {
        baseLink = new Link(URI.create("http://localhost/123"));
        jpaLinkRepository.add(baseLink);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_shouldReturnLinks() {
        List<Link> links = jpaLinkRepository.findAll();

        assertThat(links.contains(baseLink)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrl_shouldReturnLink() {
        Link link = jpaLinkRepository.findByUrl(baseLink.getUrl());

        assertThat(link).isEqualTo(baseLink);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddLink() {
        Link newLink = new Link(URI.create("http://ae"));

        jpaLinkRepository.add(newLink);
        Link link = jpaLinkRepository.findByUrl(newLink.getUrl());

        assertThat(link).isEqualTo(newLink);
    }

    @Test
    @Transactional
    @Rollback
    public void update_shouldChangeLastActivity() {
        OffsetDateTime newDateTime = OffsetDateTime.of(1, 1, 2, 1,
            1, 1, 1, ZoneOffset.UTC
        );
        baseLink.setLastActivity(newDateTime);

        jpaLinkRepository.update(baseLink);
        Link link = jpaLinkRepository.findByUrl(baseLink.getUrl());

        assertThat(link.getLastActivity().getYear()).isEqualTo(newDateTime.getYear());
        assertThat(link.getLastActivity().getMonth()).isEqualTo(newDateTime.getMonth());
    }

    @Test
    @Transactional
    @Rollback
    public void removeByUrl_shouldRemoveLink() {
        jpaLinkRepository.remove(baseLink.getUrl());
        Link link = jpaLinkRepository.findByUrl(baseLink.getUrl());

        assertThat(link).isNull();
    }
}
