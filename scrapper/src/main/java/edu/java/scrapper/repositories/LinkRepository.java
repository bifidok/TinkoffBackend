package edu.java.scrapper.repositories;

import edu.java.scrapper.models.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkRepository {
    List<Link> findAll();

    List<Link> findByCheckDateMoreThan(OffsetDateTime dateTime);

    Link findByUrl(URI url);

    void add(Link link);

    void update(Link link);

    void remove(URI url);
}
