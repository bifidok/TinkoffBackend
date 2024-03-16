package edu.java.scrapper.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Question {
    private long id;
    private Link link;
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
