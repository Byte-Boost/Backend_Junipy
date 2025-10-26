package net.byteboost.junipy.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.github.cdimascio.dotenv.Dotenv;
import net.byteboost.junipy.model.ChatMessageDocument;
import net.byteboost.junipy.repository.ChatMessageRepository;
import net.byteboost.junipy.model.UserProfile;
import net.byteboost.junipy.repository.UserProfileRepository;

@Service
public class ChatProcessingService {

    private final ChatMessageRepository repository;
    private final UserProfileRepository profileRepository;
    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private final String aiUrl;

    @Autowired
    public ChatProcessingService(ChatMessageRepository repository, UserProfileRepository profileRepository) {
        this.repository = repository;
        this.profileRepository = profileRepository;
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.aiUrl = Dotenv.load().get("AI_SERVER_URL");
    }

    public JsonNode processUserMessages(String userId) throws Exception {
        List<ChatMessageDocument> messages = repository.findByUserIdOrderByCreatedAtAsc(userId);
        UserProfile userProfile = profileRepository.findByUserId(userId);
        if (userProfile == null) {
            throw new IllegalStateException("User profile not found for userId: " + userId);
        }

        ArrayNode arr = objectMapper.createArrayNode();
        for (ChatMessageDocument m : messages) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("role", m.getRole());
            node.put("message", m.getMessage());
            if (m.getChatId() != null) node.put("chatId", m.getChatId());
            if (m.getCreatedAt() != null) node.put("createdAt", m.getCreatedAt().toString());
            arr.add(node);
        }

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("userId", userId);
        payload.set("messages", arr);

        ObjectNode profileNode = objectMapper.createObjectNode();
        profileNode.put("birthDate", userProfile.getBirthDate());
        profileNode.put("gender", userProfile.getGender());
        profileNode.put("occupation", userProfile.getOccupation());
        profileNode.put("consultationReason", userProfile.getConsultationReason());
        if (userProfile.getHealthConditions() != null) {
            profileNode.set("healthConditions", objectMapper.valueToTree(userProfile.getHealthConditions()));
        }
        if (userProfile.getAllergies() != null) {
            profileNode.set("allergies", objectMapper.valueToTree(userProfile.getAllergies()));
        }
        if (userProfile.getSurgeries() != null) {
            profileNode.set("surgeries", objectMapper.valueToTree(userProfile.getSurgeries()));
        }
        profileNode.put("activityType", userProfile.getActivityType());
        profileNode.put("activityFrequency", userProfile.getActivityFrequency());
        profileNode.put("activityDuration", userProfile.getActivityDuration());
        profileNode.put("sleepQuality", userProfile.getSleepQuality());
        profileNode.put("wakeDuringNight", userProfile.getWakeDuringNight());
        profileNode.put("bowelFrequency", userProfile.getBowelFrequency());
        profileNode.put("stressLevel", userProfile.getStressLevel());
        profileNode.put("alcoholConsumption", userProfile.getAlcoholConsumption());
        profileNode.put("smoking", userProfile.getSmoking());
        profileNode.put("hydrationLevel", userProfile.getHydrationLevel());
        profileNode.put("takesMedication", userProfile.getTakesMedication());
        profileNode.put("medicationDetails", userProfile.getMedicationDetails());

        payload.set("userProfile", profileNode);

        String body = objectMapper.writeValueAsString(payload);

        if (aiUrl == null || aiUrl.isBlank()) {
            throw new IllegalStateException("AI_SERVER_URL is not configured");
        }

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(aiUrl + "/processMessages"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readTree(response.body());
    }
}
