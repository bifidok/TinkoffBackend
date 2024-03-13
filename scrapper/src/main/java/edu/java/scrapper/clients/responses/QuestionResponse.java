package edu.java.scrapper.clients.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record QuestionResponse(List<ItemResponse> items) {
    public record ItemResponse(@JsonProperty("question_id") long id,
                               @JsonProperty("last_activity_date") OffsetDateTime lastActivity) {
    }
}
