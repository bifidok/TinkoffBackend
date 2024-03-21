package edu.java.scrapper.repositories.jpa.impl;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.ChatLinkRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;

@SuppressWarnings("MultipleStringLiterals")
@Repository
public class JpaChatLinkRepositoryImpl implements ChatLinkRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Chat> findChatsByLink(Link link) {
        return entityManager
            .createNativeQuery("select c.id, c.status from links l "
                + "join chats_links cl on l.id = cl.link_id "
                + "join chats c on c.id = cl.chat_id "
                + "where cl.link_id = :id", Chat.class)
            .setParameter("id", link.getId())
            .getResultList();
    }

    @Override
    public List<Link> findLinksByChat(Chat chat) {
        return entityManager.createNativeQuery("select l.id, l.url, l.last_activity, l.last_check_time from links l "
                + "join chats_links cl on l.id = cl.link_id "
                + "join chats c on c.id = cl.chat_id "
                + "where cl.chat_id = :id", Link.class)
            .setParameter("id", chat.getId())
            .getResultList();
    }

    @Override
    public void add(Chat chat, Link link) {
        entityManager
            .createNativeQuery("insert into chats_links(chat_id, link_id) values(:chatId,:linkId)")
            .setParameter("chatId", chat.getId())
            .setParameter("linkId", link.getId())
            .executeUpdate();
    }

    @Override
    public void remove(Chat chat, Link link) {
        entityManager
            .createNativeQuery("delete from chats_links where chat_id = :chatId and link_id = :linkId")
            .setParameter("chatId", chat.getId())
            .setParameter("linkId", link.getId())
            .executeUpdate();
    }

    @Override
    public void removeUnusedLinks() {
        entityManager
            .createNativeQuery("delete from links where id not in (select link_id from chats_links)")
            .executeUpdate();
    }
}
