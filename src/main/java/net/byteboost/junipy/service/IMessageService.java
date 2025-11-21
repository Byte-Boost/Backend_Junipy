package net.byteboost.junipy.service;

import java.util.List;
import java.util.Optional;

import net.byteboost.junipy.model.Message;

public interface IMessageService {
    List<Message> getAllMessages();
    Optional<Message> getMessageById(String id);
    Message createMessage(String userId, String message);
    Message updateMessage(String id, Message message);
    void deleteMessage(String id);
}
