package edu.java.scrapper.clients;

import edu.java.scrapper.clients.responses.RepositoryCommitsResponse;
import edu.java.scrapper.clients.responses.RepositoryResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import java.util.List;

public interface GitHubClient {
    @GetExchange("/repos/{owner}/{repo}")
    RepositoryResponse getRepoInfo(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("/repos/{owner}/{repo}/commits?since={dateTime}")
    List<RepositoryCommitsResponse> getRepoCommitsAfterDateTime(
        @PathVariable String owner, @PathVariable String repo, @PathVariable
    String dateTime
    );
}
