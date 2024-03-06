package edu.java.scrapper.repository;

import edu.java.scrapper.models.Link;
import java.util.List;

public interface LinkRepository {
    List<Link> findAll();
    void add(Link link);
    void remove(Link link);
}
