package edu.java.scrapper.clients;

import edu.java.scrapper.dto.QuestionResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface StackOverflowClient {
    @GetExchange("/questions/{ids}?site=stackoverflow")
    QuestionResponse get(@PathVariable String ids);
}
