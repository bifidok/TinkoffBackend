package edu.java.scrapper.repositories.jpa;

import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Integer>, LinkRepository {

}
