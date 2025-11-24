package net.byteboost.junipy.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class AiService {
    private final String aiUrl;
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiService() {
        this.aiUrl = Dotenv.load().get("AI_SERVER_URL");
    }

    public String getAiResponse(String prompt, String chatId, String token) {
        try {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("prompt", prompt);
            node.put("chatID", chatId);
            String body = objectMapper.writeValueAsString(node);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(aiUrl + "/chat"))
                    .version(HttpClient.Version.HTTP_1_1)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)  
                    .build();

            
            System.out.println("Sending request to AI server: " + request);
            System.out.println("Sending request with Authorization token: Bearer " + token);
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println("HTTP Response Code: " + httpResponse.statusCode());
        System.out.println("HTTP Response Body: " + httpResponse.body());
            String responseBody = httpResponse.body();
            JsonNode json = objectMapper.readTree(responseBody);
            return json.has("response") ? json.get("response").asText() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
