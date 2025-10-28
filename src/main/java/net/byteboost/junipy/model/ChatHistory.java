package net.byteboost.junipy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat_history")
public class ChatHistory {
    @Id
    private String id;
    private String userId;
    private String chatId;
    private String userMessage;
    private String aiResponse;
    private String timestamp;

    public ChatHistory(String userId, String chatId, String userMessage, String aiResponse, String timestamp) {
        this.userId = userId;
        this.chatId = chatId;
        this.userMessage = userMessage;
        this.aiResponse = aiResponse;
        this.timestamp = timestamp;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getChatId() {
        return chatId;
    }
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
    public String getUserMessage() {
        return userMessage;
    }
    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
    public String getAiResponse() {
        return aiResponse;
    }
    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
