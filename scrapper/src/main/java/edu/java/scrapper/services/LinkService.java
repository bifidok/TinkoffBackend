package edu.java.scrapper.services;

import edu.java.scrapper.exceptions.LinkDoubleCreationException;
import edu.java.scrapper.exceptions.LinkNotCorrectException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.models.Link;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LinkService {
    private final Map<Integer, Link> database;

    public LinkService() {
        database = new HashMap<>();
    }

    public void add(Link link) {
        Link sameLink = getByURI(link.getUrl());
        if (sameLink != null) {
            throw new LinkDoubleCreationException();
        }
        //TODO parse uri
        int newId = 1;
        link.setId(newId);
        database.put(newId, link);
    }

    public void remove(Link link) {
        if (link == null) {
            throw new LinkNotCorrectException("not correct link");
        }
        Link sameLink = getByURI(link.getUrl());
        if (sameLink == null) {
            throw new LinkNotFoundException("link dont exist");
        }
        database.remove(sameLink.getId());
    }

    private Link getByURI(URI uri) {
        for (var entry : database.entrySet()) {
            if (entry.getValue().getUrl().equals(uri)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
