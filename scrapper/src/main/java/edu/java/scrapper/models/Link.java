package edu.java.scrapper.models;

import java.net.URI;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
public class Link {
    @EqualsAndHashCode.Exclude
    private int id;
    @NonNull
    private URI link;
}
