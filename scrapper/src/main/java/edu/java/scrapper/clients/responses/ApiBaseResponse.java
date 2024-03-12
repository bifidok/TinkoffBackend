package edu.java.scrapper.clients.responses;

import java.time.OffsetDateTime;

public interface ApiBaseResponse {
    long getId();

    OffsetDateTime getLastActivity();
}
