package edu.java.scrapper.dto;

import edu.java.scrapper.models.ChatState;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatResponse {
    @NotNull
    private long id;
    @NotNull
    private ChatState state;
}
