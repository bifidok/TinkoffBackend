package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.linkParser.GitHubLinkParser;
import edu.java.scrapper.linkParser.LinkParser;
import edu.java.scrapper.linkParser.StackOverFlowLinkParser;
import edu.java.scrapper.linkParser.links.GitHubLink;
import edu.java.scrapper.linkParser.links.StackOverflowLink;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.services.LinkUpdater;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JdbcLinkUpdater implements LinkUpdater {
    private final JdbcGitHubLinkUpdater jdbcGitHubLinkUpdater;
    private final JdbcStackOverflowLinkUpdater jdbcStackOverflowLinkUpdater;
    private final LinkService linkService;
    private final LinkParser linkParser;
    private int linkInspectionDelayInHours;

    @Autowired
    public JdbcLinkUpdater(
        ApplicationConfig applicationConfig,
        LinkService linkService,
        JdbcGitHubLinkUpdater jdbcGitHubLinkUpdater,
        JdbcStackOverflowLinkUpdater jdbcStackOverflowLinkUpdater
    ) {
        this.jdbcGitHubLinkUpdater = jdbcGitHubLinkUpdater;
        this.jdbcStackOverflowLinkUpdater = jdbcStackOverflowLinkUpdater;
        this.linkService = linkService;
        linkInspectionDelayInHours = applicationConfig.linkCheckDelayInHours();
        linkParser = LinkParser.link(
            new GitHubLinkParser(),
            new StackOverFlowLinkParser()
        );
    }

    @Override
    public void update() {
        OffsetDateTime dateAfterWhichInspection = getDateTimeMinusDelay();
        List<Link> links = linkService.findByCheckDateMoreThan(dateAfterWhichInspection);
        for (Link link : links) {
            switch (linkParser.check(link.getUrl())) {
                case GitHubLink gitHub -> jdbcGitHubLinkUpdater.update(gitHub, link);
                case StackOverflowLink sof -> jdbcStackOverflowLinkUpdater.update(sof, link);
                default -> log.warn("Not supported link in database");
            }
        }
    }

    private OffsetDateTime getDateTimeMinusDelay() {
        //TODO hours
        return OffsetDateTime.now().minusSeconds(linkInspectionDelayInHours);
    }
}
