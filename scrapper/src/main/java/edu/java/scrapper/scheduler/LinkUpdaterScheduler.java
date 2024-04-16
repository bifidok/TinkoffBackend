package edu.java.scrapper.scheduler;

import edu.java.scrapper.services.LinkUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LinkUpdaterScheduler {
    private final LinkUpdater linkUpdater;

    @Autowired
    public LinkUpdaterScheduler(LinkUpdater linkUpdater) {
        this.linkUpdater = linkUpdater;
    }

    @Scheduled(fixedDelayString = "#{@schedulerDelay}")
    public void update() {
        log.info("Check links for updates");
        linkUpdater.update();
    }
}
