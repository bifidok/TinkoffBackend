package edu.java.scrapper.repositories.jpa;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.repositories.ChatRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long>, ChatRepository {
}
