package edu.java.bot.dto;

import jakarta.validation.constraints.NotNull;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLinkRequest {
    @NotNull
    private URI link;
}
