package net.byteboost.junipy.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.byteboost.junipy.dto.ChatMessage;

@Controller
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage handleChat(ChatMessage message) {
        
        return new ChatMessage("assistant", "Echo: " + message.getMessage());
    }
}