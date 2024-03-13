package edu.java.bot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import lombok.Data;

@Data
public class LinkUpdateRequest {
    private int id;
    @NotNull
    private URI url;
    private String description;
    @NotEmpty
    private List<Long> tgChatIds;
}
