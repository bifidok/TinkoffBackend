package edu.java.scrapper.repositories.jooq;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
import edu.java.scrapper.repositories.jooq.generated.tables.records.LinksRecord;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.repositories.jooq.generated.tables.Links.LINKS;

@Repository("jooqLinkRepository")
public class JooqLinkRepository implements LinkRepository {
    private static final RecordMapper<LinksRecord, Link> RECORD_MAPPER = r -> new Link(
        r.getId(),
        URI.create(r.getUrl()),
        r.getLastActivity(),
        r.getLastCheckTime()
    );

    private final DSLContext dslContext;

    @Autowired
    public JooqLinkRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public List<Link> findAll() {
        return dslContext
            .selectFrom(LINKS)
            .fetchInto(Link.class);
    }

    @Override
    public List<Link> findByCheckDateMoreThan(OffsetDateTime dateTime) {
        return dslContext
            .selectFrom(LINKS)
            .where(LINKS.LAST_CHECK_TIME.lessThan(dateTime))
            .fetchInto(Link.class);
    }

    @Override
    public Link findByUrl(URI url) {
        return dslContext
            .selectFrom(LINKS)
            .where(LINKS.URL.equal(url.toString()))
            .fetchOne(RECORD_MAPPER);
    }

    @Override
    public void add(Link link) {
        dslContext
            .insertInto(LINKS)
            .set(LINKS.URL, link.getUrl().toString())
            .set(LINKS.LAST_ACTIVITY, link.getLastActivity())
            .set(LINKS.LAST_CHECK_TIME, link.getLastCheckTime())
            .execute();
    }

    @Override
    public void update(Link link) {
        dslContext
            .update(LINKS)
            .set(LINKS.LAST_CHECK_TIME, link.getLastCheckTime())
            .set(LINKS.LAST_ACTIVITY, link.getLastActivity())
            .where(LINKS.ID.equal(link.getId()))
            .execute();
    }

    @Override
    public void remove(URI url) {
        dslContext
            .deleteFrom(LINKS)
            .where(LINKS.URL.equal(url.toString()))
            .execute();
    }
}
