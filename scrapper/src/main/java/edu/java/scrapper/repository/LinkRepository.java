package edu.java.scrapper.repository;

import edu.java.scrapper.models.Link;
import java.net.URI;
import java.util.List;

public interface LinkRepository {
    List<Link> findAll();

    Link findByUrl(URI url);

    void add(Link link);

    void remove(URI url);
}
