package edu.java.scrapper.repositories.jpa.impl;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.repositories.ChatRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@SuppressWarnings("MultipleStringLiterals")
public class JpaChatRepositoryImpl implements ChatRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Chat> findAll() {
        return entityManager
            .createQuery("select c from Chat c", Chat.class)
            .getResultList();
    }

    @Override
    public Chat findById(long tgChatId) {
        Chat chat = entityManager
            .createQuery("select c from Chat c where id = :id", Chat.class)
            .setParameter("id", tgChatId)
            .getResultStream()
            .findFirst()
            .orElse(null);
        if (chat != null) {
            entityManager.detach(chat);
        }
        return chat;
    }

    @Override
    public void update(Chat chat) {
        entityManager
            .createNativeQuery("update chats set status = cast(:status as state) where id = :id")
            .setParameter("id", chat.getId())
            .setParameter("status", chat.getStatus().name())
            .executeUpdate();
    }

    @Override
    public void add(Chat chat) {
        entityManager
            .createNativeQuery("insert into chats (id,status) values (:id,cast(:status as state))")
            .setParameter("id", chat.getId())
            .setParameter("status", chat.getStatus().name())
            .executeUpdate();
    }

    @Override
    public void remove(Chat chat) {
        entityManager
            .createQuery("delete from Chat where id = :id")
            .setParameter("id", chat.getId())
            .executeUpdate();
    }
}
