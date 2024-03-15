package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import edu.java.scrapper.repository.QuestionRepository;
import edu.java.scrapper.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcQuestionService implements QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public JdbcQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question findByLink(Link link) {
        return questionRepository.findByLink(link);
    }

    @Override
    public void add(Question question) {
        questionRepository.add(question);
    }

    @Override
    public void remove(Question question) {
        questionRepository.remove(question);
    }

    @Override
    public void update(Question question) {
        questionRepository.update(question);
    }
}
