package edu.java.clients;

import edu.java.dto.RepositoryResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GitHubClient {
    @GetExchange("/repos/{owner}/{repo}")
    RepositoryResponse get(@PathVariable String owner, @PathVariable String repo);
}
