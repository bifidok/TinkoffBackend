package edu.java.scrapper.repositories.jooq;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.ChatLinkRepository;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.repositories.jooq.generated.Tables.CHATS;
import static edu.java.scrapper.repositories.jooq.generated.Tables.CHATS_LINKS;
import static edu.java.scrapper.repositories.jooq.generated.Tables.LINKS;

@Repository("jooqChatLinkRepository")
public class JooqChatLinkRepository implements ChatLinkRepository {
    private final DSLContext dslContext;

    @Autowired
    public JooqChatLinkRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public List<Chat> findChatsByLink(Link link) {
        return dslContext
            .select(CHATS.ID, CHATS.STATUS)
            .from(LINKS)
            .join(CHATS_LINKS)
            .on(LINKS.ID.equal(CHATS_LINKS.LINK_ID))
            .join(CHATS)
            .on(CHATS.ID.equal(CHATS_LINKS.CHAT_ID))
            .where(CHATS_LINKS.LINK_ID.equal(link.getId()))
            .fetchInto(Chat.class);
    }

    @Override
    public List<Link> findLinksByChat(Chat chat) {
        return dslContext
            .select(LINKS.ID, LINKS.URL, LINKS.LAST_ACTIVITY, LINKS.LAST_CHECK_TIME)
            .from(LINKS)
            .join(CHATS_LINKS)
            .on(CHATS_LINKS.LINK_ID.equal(LINKS.ID))
            .join(CHATS)
            .on(CHATS.ID.equal(CHATS_LINKS.CHAT_ID))
            .where(CHATS_LINKS.CHAT_ID.equal(chat.getId()))
            .fetchInto(Link.class);
    }

    @Override
    public void add(Chat chat, Link link) {
        dslContext
            .insertInto(CHATS_LINKS)
            .set(CHATS_LINKS.CHAT_ID, chat.getId())
            .set(CHATS_LINKS.LINK_ID, link.getId())
            .execute();
    }

    @Override
    public void remove(Chat chat, Link link) {
        dslContext
            .deleteFrom(CHATS_LINKS)
            .where(CHATS_LINKS.CHAT_ID.equal(chat.getId()))
            .and(CHATS_LINKS.LINK_ID.equal(link.getId()))
            .execute();
    }

    @Override
    public void removeUnusedLinks() {
        dslContext
            .delete(LINKS)
            .where(LINKS.ID.notIn(dslContext
                .select(CHATS_LINKS.LINK_ID)
                .from(CHATS_LINKS)))
            .execute();
    }
}
