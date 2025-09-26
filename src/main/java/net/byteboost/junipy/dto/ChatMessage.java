package net.byteboost.junipy.dto;

public class ChatMessage {
    private String role;
    private String message;
    private String error;

    public ChatMessage() {}

    public ChatMessage(String role, String message, String error) {
        this.role = role;
        this.message = message;
        this.error = error;
    }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}