package edu.java.scrapper.models;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Link {
    @EqualsAndHashCode.Exclude
    private int id;
    private URI url;
    @EqualsAndHashCode.Exclude
    private OffsetDateTime lastActivity;
    @EqualsAndHashCode.Exclude
    private List<Chat> chats;

    public Link() {

    }

    public Link(URI link) {
        this.url = link;
        lastActivity = OffsetDateTime.now();
    }

    public Link(URI url, OffsetDateTime lastActivity) {
        this.url = url;
        this.lastActivity = lastActivity;
    }

}
