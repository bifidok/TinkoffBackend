package edu.java.scrapper.repositories.jooq;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.repositories.ChatRepository;
import edu.java.scrapper.repositories.jooq.generated.tables.records.ChatsRecord;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.repositories.jooq.generated.Tables.CHATS;

@Repository
public class JooqChatRepository implements ChatRepository {
    private static final RecordMapper<ChatsRecord, Chat> RECORD_MAPPER = r -> new Chat(
        r.getId(),
        ChatState.valueOf(r.getStatus().name())
    );

    private final DSLContext dslContext;

    @Autowired
    public JooqChatRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public List<Chat> findAll() {
        return dslContext
            .selectFrom(CHATS)
            .fetchInto(Chat.class);
    }

    @Override
    public Chat findById(long tgChatId) {
        return dslContext
            .selectFrom(CHATS)
            .where(CHATS.ID.equal(tgChatId))
            .fetchOne(RECORD_MAPPER);
    }

    @Override
    public void update(Chat chat) {
        dslContext
            .update(CHATS)
            .set(CHATS.STATUS, chat.getStatus())
            .where(CHATS.ID.equal(chat.getId()))
            .execute();
    }

    @Override
    public void add(Chat chat) {
        dslContext
            .insertInto(CHATS)
            .set(CHATS.ID, chat.getId())
            .set(CHATS.STATUS, chat.getStatus())
            .execute();
    }

    @Override
    public void remove(Chat chat) {
        dslContext
            .deleteFrom(CHATS)
            .where(CHATS.ID.equal(chat.getId()))
            .execute();
    }
}
