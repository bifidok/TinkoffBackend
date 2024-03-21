package edu.java.scrapper.services.jooq;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import edu.java.scrapper.repositories.QuestionRepository;
import edu.java.scrapper.services.QuestionService;
import org.springframework.transaction.annotation.Transactional;

public class JooqQuestionService implements QuestionService {
    private final QuestionRepository questionRepository;

    public JooqQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Question findByLink(Link link) {
        return questionRepository.findByLink(link);
    }

    @Override
    @Transactional
    public void add(Question question) {
        questionRepository.add(question);
    }

    @Override
    @Transactional
    public void remove(Question question) {
        questionRepository.remove(question);
    }

    @Override
    @Transactional
    public void update(Question question) {
        questionRepository.update(question);
    }
}
