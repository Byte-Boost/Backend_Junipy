package net.byteboost.junipy.dto;

public class ChatMessage {
    private String role;
    private String message;
    private String error;
    private String token;

    public ChatMessage() {}

    public ChatMessage(String role, String message, String error, String token) {
        this.role = role;
        this.message = message;
        this.error = error;
        this.token = token;
    }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}