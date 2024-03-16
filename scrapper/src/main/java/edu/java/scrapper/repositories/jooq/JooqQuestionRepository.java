package edu.java.scrapper.repositories.jooq;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.models.Question;
import edu.java.scrapper.repositories.QuestionRepository;
import edu.java.scrapper.repositories.jooq.generated.tables.records.QuestionsRecord;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.repositories.jooq.generated.Tables.QUESTIONS;

@Repository("jooqQuestionRepository")
public class JooqQuestionRepository implements QuestionRepository {
    private static final RecordMapper<QuestionsRecord, Question> RECORD_MAPPER = r -> new Question(
        r.getId(),
        r.getAnswerCount()
    );

    private final DSLContext dslContext;

    @Autowired
    public JooqQuestionRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Question findByLink(Link link) {
        Question question = dslContext
            .selectFrom(QUESTIONS)
            .where(QUESTIONS.LINK_ID.equal(link.getId()))
            .fetchOne(RECORD_MAPPER);
        if (question != null) {
            question.setLink(link);
        }
        return question;
    }

    @Override
    public void add(Question question) {
        dslContext
            .insertInto(QUESTIONS)
            .set(QUESTIONS.ID, question.getId())
            .set(QUESTIONS.LINK_ID, question.getLink().getId())
            .set(QUESTIONS.ANSWER_COUNT, question.getAnswerCount())
            .execute();
    }

    @Override
    public void remove(Question question) {
        dslContext
            .deleteFrom(QUESTIONS)
            .where(QUESTIONS.ID.equal(question.getId()))
            .execute();
    }

    @Override
    public void update(Question question) {
        dslContext
            .update(QUESTIONS)
            .set(QUESTIONS.ANSWER_COUNT, question.getAnswerCount())
            .execute();
    }
}
