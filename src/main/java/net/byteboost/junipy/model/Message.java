package net.byteboost.junipy.model;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message")
public class Message {
    @Id
    private String id;
    private String sentBy;
    private String message;
    private String timestamp;

    public Message(String sentBy, String message) {
        this.sentBy = sentBy;
        this.message = message;
        this.timestamp = new Date(System.currentTimeMillis()).toString();
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSentBy() {
        return sentBy;
    }
    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
