package net.byteboost.junipy.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.github.cdimascio.dotenv.Dotenv;
import net.byteboost.junipy.dto.ChatMessage;


@Controller
public class ChatController {
    private final String aiUrl = Dotenv.load().get("SERVICE_AI_URL");
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage handleChat(ChatMessage message) {
        try {
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
            return new ChatMessage("assistant", reply, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ChatMessage("assistant", null, "Error contacting Junipy: " + e.getMessage());
        }
    }
}