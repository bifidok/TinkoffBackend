package edu.java.scrapper.services;

import edu.java.scrapper.models.Link;
import java.net.URI;
import java.util.List;

public interface LinkService {
    List<Link> findAll();

    List<Link> findAll(long tgChatId);

    void add(long tgChatId, URI url);

    void remove(long tgChatId, URI url);
}
