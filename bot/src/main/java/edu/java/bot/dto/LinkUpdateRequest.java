package edu.java.bot.dto;

import java.net.URI;
import java.util.List;
import lombok.Data;

@Data
public class LinkUpdateRequest {
    private int id;
    private URI url;
    private String description;
    private List<Integer> tgChatIds;
}
