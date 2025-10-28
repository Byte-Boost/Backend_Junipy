package net.byteboost.junipy.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.github.cdimascio.dotenv.Dotenv;
import net.byteboost.junipy.dto.ChatMessage;
import net.byteboost.junipy.model.ChatHistory;
import net.byteboost.junipy.repository.ChatHistoryRepository;
import net.byteboost.junipy.repository.UserProfileRepository;
import net.byteboost.junipy.security.JwtUtil;


@Controller
public class ChatController {
    private final String aiUrl = Dotenv.load().get("AI_SERVER_URL");
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChatHistoryRepository chatHistoryRepository;
    private final UserProfileRepository userProfileRepository;
    private JwtUtil jwtUtils;
    public ChatController(ChatHistoryRepository chatHistoryRepository, JwtUtil jwtUtils,UserProfileRepository userProfileRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
        this.jwtUtils = jwtUtils;
        this.userProfileRepository = userProfileRepository;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage handleChat(ChatMessage message, @RequestHeader("Authorization") String authHeader) {
        try {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("prompt", message.getMessage());
            JsonNode authJson = objectMapper.readTree(authHeader);
            System.err.println(authJson.get("token").asText());
            String jwtoken = authJson.get("token").asText();
            String userId = jwtUtils.extractUserId(jwtoken);
            
            node.put("userID", userId);
            node.put("chatID", "");
            List<ChatHistory> userHistory = chatHistoryRepository.findAllByUserId(userId);
            node.set("userHistory", objectMapper.valueToTree(userHistory));

            node.set("userInfo",  objectMapper.valueToTree(userProfileRepository.findByUserId(userId)));
            
            String body = objectMapper.writeValueAsString(node);
            System.out.println("Request Body: " + body);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(aiUrl + "/chat"))
            .version(HttpClient.Version.HTTP_1_1) 
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .header("Content-Type", "application/json")
            .build();
            
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            String responseBody = httpResponse.body();
            
            JsonNode json = objectMapper.readTree(responseBody);
            System.out.println("Response Body: " + responseBody);
            String reply = json.has("response") ? json.get("response").asText() : "Error contacting Junipy verify your internet connection";
            String chatId = json.has("chatID") ? json.get("chatID").asText() : null;


            ChatHistory chatHistory = new ChatHistory(userId, chatId, message.getMessage(), reply, LocalDateTime.now().toString());
            chatHistoryRepository.save(chatHistory);
            return new ChatMessage("assistant", reply, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ChatMessage("assistant", null, "Error contacting Junipy: " + e.getMessage());
        }
    }
}