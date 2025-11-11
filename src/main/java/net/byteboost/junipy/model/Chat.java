package net.byteboost.junipy.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
public class Chat {
    @Id
    private String id;
    
    private List<Message> messages;
    
    public Chat(String id, List<Message> messages) {
        this.id = id;
        this.messages = messages;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
