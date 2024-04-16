package edu.java.scrapper.models;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public GitHubRepository(int id, OffsetDateTime lastCommitDate) {
        this.id = id;
        this.lastCommitDate = lastCommitDate;
    }
}
