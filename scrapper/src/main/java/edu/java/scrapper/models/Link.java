package edu.java.scrapper.models;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.Data;

@Data
public class Link {
    private int id;
    private URI url;
    private List<Chat> chats;

    public Link() {

    }

    public Link(URI link) {
        this.url = link;
    }

    public Link(int id, URI link) {
        this.id = id;
        this.url = link;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Link anotherLink = (Link) o;
        return Objects.equals(this.url, anotherLink.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
