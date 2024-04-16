package edu.java.scrapper.dto;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class LinkResponse {
    private Integer id;
    @NonNull
    private URI uri;
}
