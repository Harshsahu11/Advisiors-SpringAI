package com.detrox.advisiors_app.controller;

import com.detrox.advisiors_app.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam String query) {
        return ResponseEntity.ok(chatService.chat(query));
    }

    @GetMapping("stream-chat")
    public ResponseEntity<Flux<String>> streamChat(@RequestParam String query){
        return ResponseEntity.ok(chatService.streamChat(query));
    }
}