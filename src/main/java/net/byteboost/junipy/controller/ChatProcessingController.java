package net.byteboost.junipy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import net.byteboost.junipy.service.ChatProcessingService;

@RestController
@RequestMapping("/chat")
public class ChatProcessingController {

    private final ChatProcessingService processingService;

    @Autowired
    public ChatProcessingController(ChatProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/process/{userId}")
    public ResponseEntity<?> processUserMessages(@PathVariable String userId) {
        try {
            JsonNode result = processingService.processUserMessages(userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing user messages: " + e.getMessage());
        }
    }
}
