package net.byteboost.junipy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.byteboost.junipy.model.Message;
import net.byteboost.junipy.repository.MessageRepository;

@Service
public class MessageService implements IMessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> getMessageById(String id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message createMessage(String userId, String message) {
        Message newMessage = new Message(userId, message);
        return messageRepository.save(newMessage);
    }

    @Override
    public Message updateMessage(String id, Message message) {
        message.setId(id);
        return messageRepository.save(message);
    }

    @Override
    public void deleteMessage(String id) {
        messageRepository.deleteById(id);
    }
}
