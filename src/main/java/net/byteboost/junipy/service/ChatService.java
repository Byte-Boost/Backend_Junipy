package net.byteboost.junipy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.byteboost.junipy.model.Chat;
import net.byteboost.junipy.repository.ChatRepository;

@Service
public class ChatService implements IChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @Override
    public Optional<Chat> getChatById(String id) {
        return chatRepository.findById(id);
    }

    @Override
    public List<Chat> getChatsByUserId(String userId) {
        return chatRepository.findByUserId(userId);
    }

    @Override
    public Chat createChat(String userId) {
        Chat chat = new Chat(userId);
        return chatRepository.save(chat);
    }

    @Override
    public Chat updateChat(String id, Chat chat) {
        chat.setId(id);
        return chatRepository.save(chat);
    }

    @Override
    public void deleteChat(String id) {
        chatRepository.deleteById(id);
    }
}
