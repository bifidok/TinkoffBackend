package edu.java.bot.dto;

import edu.java.bot.enums.ChatState;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatUpdateRequest {
    @NotNull
    private long id;
    @NotNull
    private ChatState state;
}
