package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.models.GitHubRepository;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repository.GitHubRepositoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class JdbcGitHubRepositoryRepository implements GitHubRepositoryRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcGitHubRepositoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public GitHubRepository findByLink(Link link) {
        String query = String.format("select * from repositories where link_id = %d", link.getId());
        try {
            GitHubRepository gitHubRepository =
                jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(GitHubRepository.class));
            gitHubRepository.setLink(link);
            return gitHubRepository;
        } catch (DataAccessException exception) {
            log.info(exception.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public void add(GitHubRepository repository) {
        jdbcTemplate.update("insert into repositories(link_id, last_commit_date) values (?,?)",
            repository.getLink().getId(), repository.getLastCommitDate()
        );
    }

    @Override
    @Transactional
    public void remove(GitHubRepository repository) {
        jdbcTemplate.update("delete from repositories where id = ?", repository.getId());
    }

    @Override
    @Transactional
    public void update(GitHubRepository repository) {
        jdbcTemplate.update(
            "update repositories set last_commit_date = ? where id = ?",
            repository.getLastCommitDate(),
            repository.getId()
        );
    }
}
