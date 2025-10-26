package net.byteboost.junipy.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.github.cdimascio.dotenv.Dotenv;
import net.byteboost.junipy.dto.ChatMessage;
import net.byteboost.junipy.model.ChatMessageDocument;
import net.byteboost.junipy.repository.ChatMessageRepository;


@Controller
public class ChatController {
    private final String aiUrl = Dotenv.load().get("AI_SERVER_URL");
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ChatMessageRepository messageRepository;

    public ChatController() {}

    @Autowired
    public ChatController(ChatMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage handleChat(ChatMessage message) {
        try {
            try {
                if (messageRepository != null) {
                    ChatMessageDocument userDoc = new ChatMessageDocument(
                        "user",
                        message.getMessage(),
                        null,
                        null,
                        message.getUserId(),
                        message.getChatId()
                    );
                    messageRepository.save(userDoc);
                }
            } catch (Exception saveEx) {
                saveEx.printStackTrace();
            }

            ObjectNode node = objectMapper.createObjectNode();
            node.put("prompt", message.getMessage());
            String body = objectMapper.writeValueAsString(node);

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