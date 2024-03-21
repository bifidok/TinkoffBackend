package edu.java.scrapper.repositories.jpa.impl;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@SuppressWarnings("MultipleStringLiterals")
public class JpaLinkRepositoryImpl implements LinkRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Link> findAll() {
        return entityManager
            .createQuery("select l from Link l", Link.class)
            .getResultList();
    }

    @Override
    public List<Link> findByCheckDateMoreThan(OffsetDateTime dateTime) {
        return entityManager
            .createQuery("select l from Link l where lastCheckTime < :dateTime", Link.class)
            .setParameter("dateTime", dateTime)
            .getResultList();
    }

    @Override
    public Link findByUrl(URI url) {
        return entityManager
            .createQuery("select l from Link l where url = :url", Link.class)
            .setParameter("url", url)
            .getResultStream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public void add(Link link) {
        entityManager
            .createNativeQuery("insert into links (url,last_activity,last_check_time) "
                + "values (:url,:lastActivity,:lastCheckTime)")
            .setParameter("url", link.getUrl().toString())
            .setParameter("lastActivity", link.getLastActivity())
            .setParameter("lastCheckTime", link.getLastCheckTime())
            .executeUpdate();
    }

    @Override
    public void update(Link link) {
        entityManager
            .createNativeQuery("update links set last_activity = :lastActivity, last_check_time = :lastCheckTime "
                + "where id = :id")
            .setParameter("id", link.getId())
            .setParameter("lastActivity", link.getLastActivity())
            .setParameter("lastCheckTime", link.getLastCheckTime())
            .executeUpdate();
    }

    @Override
    public void remove(URI url) {
        entityManager
            .createQuery("delete from Link where url = :url")
            .setParameter("url", url)
            .executeUpdate();
    }
}
