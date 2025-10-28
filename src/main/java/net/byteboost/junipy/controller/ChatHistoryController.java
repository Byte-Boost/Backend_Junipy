package net.byteboost.junipy.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.byteboost.junipy.model.ChatHistory;
import net.byteboost.junipy.repository.ChatHistoryRepository;

import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class ChatHistoryController {

    private final ChatHistoryRepository chatHistoryRepository;

    public ChatHistoryController(ChatHistoryRepository chatHistoryRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
    }

    @GetMapping("/user-history")
    public ResponseEntity<List<ChatHistory>> getUserHistory(@RequestParam String userId) {
        List<ChatHistory> history = chatHistoryRepository.findAllByUserId(userId);
        return ResponseEntity.ok(history);
    }
}