package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public abstract class LinkRepositoryTest extends IntegrationTest {
    private final LinkRepository linkRepository;
    private Link baseLink;

    @BeforeEach
    public void initEach() {
        baseLink = new Link(URI.create("http://localhost/123"));
        linkRepository.add(baseLink);
        baseLink = linkRepository.findByUrl(baseLink.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void     findAll_shouldReturnLinks() {
        List<Link> links = linkRepository.findAll();

        assertThat(links.contains(baseLink)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrl_shouldReturnLink() {
        Link link = linkRepository.findByUrl(baseLink.getUrl());

        assertThat(link).isEqualTo(baseLink);
    }

    @Test
    @Transactional
    @Rollback
    public void add_shouldAddLink() {
        Link newLink = new Link(URI.create("http://ae"));

        linkRepository.add(newLink);
        Link link = linkRepository.findByUrl(newLink.getUrl());

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

        linkRepository.update(baseLink);
        Link link = linkRepository.findByUrl(baseLink.getUrl());

        assertThat(link.getLastActivity().getYear()).isEqualTo(newDateTime.getYear());
        assertThat(link.getLastActivity().getMonth()).isEqualTo(newDateTime.getMonth());
    }

    @Test
    @Transactional
    @Rollback
    public void removeByUrl_shouldRemoveLink() {
        linkRepository.remove(baseLink.getUrl());
        Link link = linkRepository.findByUrl(baseLink.getUrl());

        assertThat(link).isNull();
    }
}
