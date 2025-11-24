package net.byteboost.junipy.controller;

import java.util.Optional;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.security.Principal;

import net.byteboost.junipy.dto.ChatMessage;
import net.byteboost.junipy.model.Chat;
import net.byteboost.junipy.model.Message;
import net.byteboost.junipy.service.IChatService;
import net.byteboost.junipy.service.IMessageService;
import net.byteboost.junipy.service.AiService;

@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final IChatService chatService;
    private final IMessageService messageService;
    private final AiService aiService;

    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate,
                                   IChatService chatService,
                                   IMessageService messageService,
                                   AiService aiService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
        this.messageService = messageService;
        this.aiService = aiService;
    }

    @MessageMapping("/chat/{chatId}/send")
    public void sendMessage(@DestinationVariable String chatId,
                            @Payload ChatMessage chatMessage,
                            Principal principal,
                        SimpMessageHeaderAccessor headerAccessor) {

        String userId = principal != null ? principal.getName() : null;

        Message saved = messageService.createMessage("user", chatMessage.getMessage());
        
        String authorizationHeader = headerAccessor.getNativeHeader("Authorization") != null ?
            ((java.util.List<String>) headerAccessor.getNativeHeader("Authorization")).get(0) : null;
        System.out.println("Authorization Header: " + authorizationHeader);
            String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Removes the "Bearer " part
            System.out.println("Extracted token: " + token);

        } else {
            System.out.println("No Bearer token found.");
        }

        Optional<Chat> opt = chatService.getChatById(chatId);
        if (opt.isPresent()) {
            Chat chat = opt.get();
            chat.getMessages().add(saved);
            chatService.updateChat(chatId, chat);
        }

        ChatMessage outbound = new ChatMessage("user", saved.getMessage(), null);
        messagingTemplate.convertAndSend("/topic/chat/" + chatId, outbound);

        String role = chatMessage.getRole();
        if (role == null || "user".equalsIgnoreCase(role)) {
            String reply = aiService.getAiResponse(chatMessage.getMessage(), chatId, token);
            if (reply != null) {
                Message assistantSaved = messageService.createMessage("assistant", reply);
                if (opt.isPresent()) {
                    Chat chat = opt.get();
                    chat.getMessages().add(assistantSaved);
                    chatService.updateChat(chatId, chat);
                }
                ChatMessage assistantOutbound = new ChatMessage("assistant", reply, null);
                messagingTemplate.convertAndSend("/topic/chat/" + chatId, assistantOutbound);
            } else {
                ChatMessage err = new ChatMessage("assistant", null, "Error contacting AI service");
                messagingTemplate.convertAndSend("/topic/chat/" + chatId, err);
            }
        }
    }

}
