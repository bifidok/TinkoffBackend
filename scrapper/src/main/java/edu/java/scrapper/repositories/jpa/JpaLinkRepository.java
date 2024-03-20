package edu.java.scrapper.repositories.jpa;

import edu.java.scrapper.models.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import edu.java.scrapper.repositories.ChatRepository;
import edu.java.scrapper.repositories.LinkRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Integer>, LinkRepository {

}
