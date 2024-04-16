package edu.java.bot.controllers;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.services.TGBot;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
@Slf4j
public class UpdateController {
    private final TGBot tgBot;

    @Autowired
    public UpdateController(TGBot tgBot) {
        this.tgBot = tgBot;
    }

    @PostMapping
    public HttpStatus checkUpdate(@RequestBody @Valid LinkUpdateRequest linkUpdate) {
        tgBot.updateRequest(linkUpdate);
        return HttpStatus.OK;
    }
}
