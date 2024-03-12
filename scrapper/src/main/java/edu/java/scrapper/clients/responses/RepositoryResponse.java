package edu.java.scrapper.clients.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record RepositoryResponse(@JsonProperty("id") long id,
                                 @JsonProperty("updated_at") OffsetDateTime lastActivity) implements ApiBaseResponse {
    @Override
    public long getId() {
        return id;
    }

    @Override
    public OffsetDateTime getLastActivity() {
        return lastActivity;
    }
}
