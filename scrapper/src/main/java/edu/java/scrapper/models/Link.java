package edu.java.scrapper.models;

import java.net.URI;
import java.util.Objects;
import lombok.Data;
import lombok.NonNull;

@Data
public class Link {
    private int id;
    @NonNull
    private URI link;

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Link anotherLink = (Link) o;
        return Objects.equals(this.link, anotherLink.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }
}
