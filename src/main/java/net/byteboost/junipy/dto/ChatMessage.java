package net.byteboost.junipy.dto;

public class ChatMessage {
    private String role;
    private String message;
    private String error;
    private String userId;
    private String chatId;

    public ChatMessage() {}

    public ChatMessage(String role, String message, String error) {
        this.role = role;
        this.message = message;
        this.error = error;
    }
    public ChatMessage(String role, String message, String error, String userId, String chatId) {
        this.role = role;
        this.message = message;
        this.error = error;
        this.userId = userId;
        this.chatId = chatId;
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
}