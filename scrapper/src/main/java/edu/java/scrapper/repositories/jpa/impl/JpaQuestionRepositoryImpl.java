package edu.java.scrapper.repositories.jpa.impl;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import edu.java.scrapper.repositories.QuestionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SuppressWarnings("MultipleStringLiterals")
public class JpaQuestionRepositoryImpl implements QuestionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Question findByLink(Link link) {
        return entityManager
            .createQuery("select q from Question q where link.id = :linkId", Question.class)
            .setParameter("linkId", link.getId())
            .getResultStream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public void add(Question question) {
        entityManager
            .createNativeQuery("insert into questions (id, link_id, answer_count) "
                + "values (:id,:linkId,:answerCount)")
            .setParameter("id", question.getId())
            .setParameter("linkId", question.getLink().getId())
            .setParameter("answerCount", question.getAnswerCount())
            .executeUpdate();
    }

    @Override
    public void remove(Question question) {
        entityManager
            .createQuery("delete from Question where id = :id")
            .setParameter("id", question.getId())
            .executeUpdate();
    }

    @Override
    public void update(Question question) {
        entityManager
            .createNativeQuery("update questions set answer_count = :answerCount "
                + "where id = :id")
            .setParameter("id", question.getId())
            .setParameter("answerCount", question.getAnswerCount())
            .executeUpdate();
    }
}
