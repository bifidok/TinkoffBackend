package edu.java.scrapper.dto;

import edu.java.scrapper.models.ChatState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatUpdateRequest {
    @NotNull
    private Long id;
    @NotNull
    private ChatState state;
}
