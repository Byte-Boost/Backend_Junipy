package net.byteboost.junipy.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.github.cdimascio.dotenv.Dotenv;
import net.byteboost.junipy.dto.ChatMessage;
import net.byteboost.junipy.model.ChatMessageDocument;
import net.byteboost.junipy.repository.ChatMessageRepository;
import net.byteboost.junipy.security.JwtUtil;
import net.byteboost.junipy.service.UserService;


@Controller
public class ChatController {
    private final String aiUrl = Dotenv.load().get("AI_SERVER_URL");
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ChatMessageRepository messageRepository;
    private JwtUtil jwtUtil;
    public ChatController() {}

    @Autowired
    public ChatController(ChatMessageRepository messageRepository, JwtUtil jwtUtil) {
        this.messageRepository = messageRepository;
        this.jwtUtil = jwtUtil;
    }
    @Autowired
    private UserService userService;
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage handleChat(ChatMessage message) {
          try {
                String token = message.getToken();

                token = token.replace("Bearer ", "");
                System.out.println("Token retrieved: " + token);
                String userId = jwtUtil.extractUserId(token);

            try {
                if (messageRepository != null) {
                    ChatMessageDocument userDoc = new ChatMessageDocument(
                        "user",
                        message.getMessage(),
                        null,
                        null,
                        userId,
                        message.getChatId()
                    );
                    messageRepository.save(userDoc);
                }
            } catch (Exception saveEx) {
                saveEx.printStackTrace();
            }

            ObjectNode node = objectMapper.createObjectNode();
            node.put("prompt", message.getMessage());
            node.put("userId", userId);
            node.put("chatId", message.getChatId());
            node.putPOJO("userInfo", userService.getUserProfile(userId) != null ? userService.getUserProfile(userId) : new ObjectNode(objectMapper.getNodeFactory()));
            String body = objectMapper.writeValueAsString(node);
            System.out.println(body);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(aiUrl + "/chat"))
            .version(HttpClient.Version.HTTP_1_1) 
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .header("Content-Type", "application/json")
            .build();
            
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            String responseBody = httpResponse.body();
            
            JsonNode json = objectMapper.readTree(responseBody);

            String reply = json.has("response") ? json.get("response").asText() : "Error contacting Junipy verify your internet connection";
            try {
                if (messageRepository != null) {
                    ChatMessageDocument assistantDoc = new ChatMessageDocument(
                        "assistant",
                        reply,
                        null,
                        null,
                        message.getUserId(),
                        message.getChatId()
                    );
                    messageRepository.save(assistantDoc);
                }
            } catch (Exception saveEx) {
                saveEx.printStackTrace();
            }

            return new ChatMessage("assistant", reply, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ChatMessage("assistant", null, "Error contacting Junipy: " + e.getMessage());
        }
    }
}