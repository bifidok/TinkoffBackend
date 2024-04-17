package edu.java.scrapper.repositories.jdbc;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import edu.java.scrapper.repositories.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class JdbcQuestionRepository implements QuestionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcQuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Question findByLink(Link link) {
        try {
            Question question =
                jdbcTemplate.queryForObject(
                    "select * from questions where link_id = ?",
                    new BeanPropertyRowMapper<>(Question.class),
                    link.getId()
                );
            question.setLink(link);
            return question;
        } catch (DataAccessException exception) {
            log.info(exception.getMessage());
        }
        return null;
    }

    @Override
    public void add(Question question) {
        jdbcTemplate.update("insert into questions(id,link_id,answer_count) values(?,?,?)",
            question.getId(), question.getLink().getId(), question.getAnswerCount()
        );
    }

    @Override
    public void remove(Question question) {
        jdbcTemplate.update("delete from questions where id = ?", question.getId());
    }

    @Override
    public void update(Question question) {
        jdbcTemplate.update(
            "update questions set answer_count = ? where id = ?",
            question.getAnswerCount(),
            question.getId()
        );
    }
}
