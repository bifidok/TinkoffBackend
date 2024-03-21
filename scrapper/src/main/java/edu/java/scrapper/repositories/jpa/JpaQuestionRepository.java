package edu.java.scrapper.repositories.jpa;

import edu.java.scrapper.models.Question;
import edu.java.scrapper.repositories.QuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaQuestionRepository extends JpaRepository<Question, Long>, QuestionRepository {
}
