package net.byteboost.junipy.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.byteboost.junipy.model.Chat;
import net.byteboost.junipy.security.JwtUtil;
import net.byteboost.junipy.service.IChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private JwtUtil jwtUtils;
    private final IChatService chatService;

    public ChatController(IChatService chatService, JwtUtil jwtUtils) {
        this.chatService = chatService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ResponseEntity<List<Chat>> all(@RequestHeader("Authorization") String authHeader) {
        String jwtToken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtToken);
        return ResponseEntity.ok(chatService.getChatsByUserId(userId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        chatService.deleteChat(id); 
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Chat> postChat(@RequestHeader("Authorization") String authHeader) {
        String jwtToken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtToken);
        Chat newChat = chatService.createChat(userId);
        return ResponseEntity.status(201).body(newChat);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Chat> updateChat(@PathVariable String id, @RequestBody Chat chat) {
        Chat updatedChat = chatService.updateChat(id, chat);
        return ResponseEntity.ok(updatedChat);
    }

    @GetMapping("/user-chats")
    public ResponseEntity<List<Chat>> getChatsByUserId(@RequestHeader("Authorization") String authHeader) {
        String jwtToken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtToken);
        List<Chat> chats = chatService.getChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }
}
