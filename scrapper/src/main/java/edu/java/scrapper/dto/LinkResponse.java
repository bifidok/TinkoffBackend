package edu.java.scrapper.dto;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class LinkResponse {
    private int id;
    @NonNull
    private URI uri;
}
