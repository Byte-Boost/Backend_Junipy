package net.byteboost.junipy.dto;

import java.util.Map;

public class ChatMessage {
    private String role;
    private String token;
    private String message;
    private String error;
    private String userId;
    private String chatId;
    private Map<String, String> userInfo;

    public ChatMessage() {}

    public ChatMessage(String role, String message, String error) {
        this.role = role;
        this.message = message;
        this.error = error;
    }
    public ChatMessage(String role, String message, String error, String userId, String chatId, Map<String, String> userInfo) {
        this.role = role;
        this.message = message;
        this.error = error;
        this.userId = userId;
        this.chatId = chatId;
        this.userInfo = userInfo;
    }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getChatId() { return chatId; }
    public void setChatId(String chatId) { this.chatId = chatId; }
    public Map<String, String> getUserInfo() { return userInfo; }
    public void setUserInfo(Map<String, String> userInfo) { this.userInfo = userInfo; }
    public String getToken() { return token; }
}