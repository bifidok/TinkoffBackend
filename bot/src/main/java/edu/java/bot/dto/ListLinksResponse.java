package edu.java.bot.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListLinksResponse {
    @NotNull
    private List<LinkResponse> links;
    private int size;
}
