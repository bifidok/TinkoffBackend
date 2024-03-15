package edu.java.scrapper.services;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;

public interface QuestionService {
    Question findByLink(Link link);

    void add(Question question);

    void remove(Question question);

    void update(Question question);
}
