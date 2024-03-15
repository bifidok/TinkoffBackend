package edu.java.scrapper.repository;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;

public interface QuestionRepository {
    Question findByLink(Link link);

    void add(Question question);

    void remove(Question question);

    void update(Question question);
}
