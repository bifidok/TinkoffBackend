package edu.java.bot.dto;

import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data
public class ListLinksResponse {
    @NonNull
    private List<LinkResponse> links;
    @NonNull
    private int size;
}
