package edu.java.bot.controllers;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.exceptions.LinkUpdateNotCorrectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
@Slf4j
public class UpdateController {

    @PostMapping
    public HttpStatus checkUpdate(@RequestBody LinkUpdateRequest linkUpdate) {
        if (linkUpdate == null || linkUpdate.getDescription() == null) {
            throw new LinkUpdateNotCorrectException("linkUpdate not correct");
        }
        return HttpStatus.OK;
    }
}
