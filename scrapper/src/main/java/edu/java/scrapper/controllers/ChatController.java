package edu.java.scrapper.controllers;

import edu.java.scrapper.dto.ChatResponse;
import edu.java.scrapper.dto.ChatUpdateRequest;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.services.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{id}")
    public ChatResponse get(@PathVariable("id") long tgChatId) {
        Chat chat = chatService.findById(tgChatId);
        ChatResponse response = null;
        if (chat != null) {
            response = new ChatResponse(chat.getId(), chat.getStatus());
        }
        return response;
    }

    @PatchMapping
    public HttpStatus update(@RequestBody @Valid ChatUpdateRequest chatUpdateRequest) {
        chatService.update(chatUpdateRequest.getId(), chatUpdateRequest.getState());
        return HttpStatus.OK;
    }

    @PostMapping("/{id}")
    public HttpStatus register(@PathVariable("id") long tgChatId) {
        chatService.register(tgChatId);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable("id") long tgChatId) {
        chatService.unregister(tgChatId);
        return HttpStatus.OK;
    }
}
