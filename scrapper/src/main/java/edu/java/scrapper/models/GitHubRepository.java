package edu.java.scrapper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "repositories")
public class GitHubRepository {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @OneToOne
    @JoinColumn(name = "link_id", referencedColumnName = "id")
    private Link link;
    @Column(name = "last_commit_date")
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
