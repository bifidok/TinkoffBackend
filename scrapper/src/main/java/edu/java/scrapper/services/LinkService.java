package edu.java.scrapper.services;

import edu.java.scrapper.models.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {
    List<Link> findAll();

    List<Link> findAll(long tgChatId);

    List<Link> findByCheckDateMoreThan(OffsetDateTime dateTime);

    Link findByUrl(URI url);

    void add(long tgChatId, URI url);

    void update(Link link);

    void remove(long tgChatId, URI url);

    void remove(URI url);

    void removeUnused();
}
