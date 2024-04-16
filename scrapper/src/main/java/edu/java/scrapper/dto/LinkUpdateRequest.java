package edu.java.scrapper.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkUpdateRequest {
    private Integer id;
    @NotNull
    private URI url;
    private String description;
    @NotEmpty
    private List<Long> tgChatIds;
}
