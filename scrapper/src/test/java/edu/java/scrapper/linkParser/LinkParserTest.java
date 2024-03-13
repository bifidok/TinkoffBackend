package edu.java.scrapper.linkParser;

import edu.java.scrapper.linkParser.links.GitHubLink;
import edu.java.scrapper.linkParser.links.LinkParseResponse;
import edu.java.scrapper.linkParser.links.NotSupportedLink;
import edu.java.scrapper.linkParser.links.StackOverflowLink;
import java.net.URI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkParserTest {
    private static LinkParser linkParser;

    @BeforeAll
    public static void initAll() {
        linkParser = LinkParser.link(
            new StackOverFlowLinkParser(),
            new GitHubLinkParser()
        );
    }

    @Test
    public void check_whenGitHubLink() {
        String owner = "owner";
        String repo = "repo";
        URI url = URI.create(String.format("https://github.com/%s/%s", owner, repo));
        LinkParseResponse gitHubLink = linkParser.check(url);

        assertThat(gitHubLink.getClass()).isEqualTo(GitHubLink.class);
        assertThat(((GitHubLink) gitHubLink).owner()).isEqualTo(owner);
        assertThat(((GitHubLink) gitHubLink).repo()).isEqualTo(repo);
    }

    @Test
    public void check_whenStackOverFlowLink() {
        String id = "123";
        URI url = URI.create(String.format("https://stackoverflow.com/questions/%s/cors-enable-in-servlet", id));

        LinkParseResponse stackOverflowLink = linkParser.check(url);

        assertThat(stackOverflowLink.getClass()).isEqualTo(StackOverflowLink.class);
        assertThat(((StackOverflowLink) stackOverflowLink).questionId()).isEqualTo(id);
    }

    @Test
    public void check_whenNotSupportedLink() {
        URI url = URI.create("https://notsupported.com/questions/22/cors-enable-in-servlet");

        LinkParseResponse response = linkParser.check(url);

        assertThat(response.getClass()).isEqualTo(NotSupportedLink.class);
    }
}
