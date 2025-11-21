package net.byteboost.junipy.service;

import java.util.List;
import java.util.Optional;

import net.byteboost.junipy.model.Chat;

public interface IChatService {
    List<Chat> getAllChats();
    Optional<Chat> getChatById(String id);
    List<Chat> getChatsByUserId(String userId);
    Chat createChat(String userId);
    Chat updateChat(String id, Chat chat);
    void deleteChat(String id);
}
