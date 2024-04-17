package edu.java.scrapper.services.linkUpdaters;

import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.linkParser.GitHubLinkParser;
import edu.java.scrapper.linkParser.LinkParser;
import edu.java.scrapper.linkParser.StackOverFlowLinkParser;
import edu.java.scrapper.linkParser.links.GitHubLink;
import edu.java.scrapper.linkParser.links.StackOverflowLink;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.services.LinkService;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LinkUpdater {
    private final static String DEFAULT_ERROR_MESSAGE = "Something went wrong for link ";
    private final GitHubLinkUpdater gitHubLinkUpdater;
    private final StackOverflowLinkUpdater stackOverflowLinkUpdater;
    private final LinkService linkService;
    private final LinkParser linkParser;
    private int linkInspectionDelayInHours;

    @Autowired
    public LinkUpdater(
        ApplicationConfig applicationConfig,
        LinkService linkService,
        GitHubLinkUpdater jdbcGitHubLinkUpdater,
        StackOverflowLinkUpdater jdbcStackOverflowLinkUpdater
    ) {
        this.gitHubLinkUpdater = jdbcGitHubLinkUpdater;
        this.stackOverflowLinkUpdater = jdbcStackOverflowLinkUpdater;
        this.linkService = linkService;
        linkInspectionDelayInHours = applicationConfig.linkCheckDelayInHours();
        linkParser = LinkParser.link(
            new GitHubLinkParser(),
            new StackOverFlowLinkParser()
        );
    }

    public void update() {
        OffsetDateTime dateAfterWhichInspection = getDateTimeMinusDelay();
        linkService.removeUnused();
        List<Link> links = linkService.findByCheckDateMoreThan(dateAfterWhichInspection);
        for (Link link : links) {
            switch (linkParser.check(link.getUrl())) {
                case GitHubLink gitHub:
                    if (!gitHubLinkUpdater.update(gitHub, link)) {
                        log.warn(DEFAULT_ERROR_MESSAGE, link.getUrl());
                    }
                    break;
                case StackOverflowLink sof:
                    if (!stackOverflowLinkUpdater.update(sof, link)) {
                        log.warn(DEFAULT_ERROR_MESSAGE, link.getUrl());
                    }
                    break;
                default:
                    linkService.remove(link.getUrl());
            }
        }
    }

    private OffsetDateTime getDateTimeMinusDelay() {
        //TODO hours
        return OffsetDateTime.now().minusSeconds(linkInspectionDelayInHours);
    }
}
