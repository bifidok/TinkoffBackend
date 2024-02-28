package edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepositoryResponse(@JsonProperty("id") long id,
                                 @JsonProperty("updated_at") String lastActivity) {
}
