package edu.java.scrapper.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkUpdateRequest {
    private int id;
    @NotNull
    private URI url;
    private String description;
    @NotEmpty
    private List<Long> tgChatIds;
}
