package edu.java.scrapper.clients.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record RepositoryCommitsResponse(@JsonProperty("sha") String sha, @JsonProperty("commit") Commit commit) {
    public record Commit(@JsonProperty("message") String message, @JsonProperty("author") Author author) {
        public record Author(@JsonProperty("name") String name, @JsonProperty("date") OffsetDateTime commitDateTime){

        }
    }
}
