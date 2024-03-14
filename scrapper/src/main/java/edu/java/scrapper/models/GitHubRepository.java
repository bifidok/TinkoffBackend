package edu.java.scrapper.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class GitHubRepository {
    private int id;
    private Link link;
    private OffsetDateTime lastCommitDate;

    public GitHubRepository(Link link, OffsetDateTime lastCommitDate) {
        this.link = link;
        this.lastCommitDate = lastCommitDate;
    }
}
