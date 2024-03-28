package edu.java.scrapper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @Column(name = "id")
    private long id;
    @OneToOne
    @JoinColumn(name = "link_id", referencedColumnName = "id")
    private Link link;
    @Column(name = "answer_count")
    private int answerCount;

    public Question(long id, Link link) {
        this.id = id;
        this.link = link;
    }

    public Question(long id, int answerCount) {
        this.id = id;
        this.answerCount = answerCount;
    }
}
