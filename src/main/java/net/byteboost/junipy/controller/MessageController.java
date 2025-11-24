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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.byteboost.junipy.model.Message;
import net.byteboost.junipy.security.JwtUtil;
import net.byteboost.junipy.service.IMessageService;

@RestController
public class MessageController {
    private JwtUtil jwtUtils;
    private final IMessageService messageService;

    public MessageController(IMessageService messageService, JwtUtil jwtUtils) {
        this.messageService = messageService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ResponseEntity<List<Message>> all() {return ResponseEntity.ok(messageService.getAllMessages());}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        messageService.deleteMessage(id); 
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Message> postMessage(@RequestHeader("Authorization") String authHeader, @RequestParam String message) {
        String jwtToken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtToken);
        Message newMessage = messageService.createMessage(userId, message);
        return ResponseEntity.status(201).body(newMessage);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable String id, @RequestBody Message message) {
        Message updatedMessage = messageService.updateMessage(id, message);
        return ResponseEntity.ok(updatedMessage);
    }
}
