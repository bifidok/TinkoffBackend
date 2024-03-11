package edu.java.scrapper.dto;

import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkUpdateRequest {
    private int id;
    private URI url;
    private String description;
    private List<Integer> tgChatIds;
}
