package net.byteboost.junipy.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat_messages")
public class ChatMessageDocument {
    @Id
    private String id;
    private String role;
    private String message;
    private String error;
    private String room;
    private Instant createdAt;
    private String userId;
    private String chatId;

    public ChatMessageDocument() {}

    public ChatMessageDocument(String role, String message, String error, String room) {
        this.role = role;
        this.message = message;
        this.error = error;
        this.room = room;
        this.createdAt = Instant.now();
    }

    public ChatMessageDocument(String role, String message, String error, String room, String userId, String chatId) {
        this.role = role;
        this.message = message;
        this.error = error;
        this.room = room;
        this.userId = userId;
        this.chatId = chatId;
        this.createdAt = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getChatId() { return chatId; }
    public void setChatId(String chatId) { this.chatId = chatId; }
}
