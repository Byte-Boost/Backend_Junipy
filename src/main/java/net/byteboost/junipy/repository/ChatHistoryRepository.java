package net.byteboost.junipy.repository;

import net.byteboost.junipy.model.ChatHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatHistoryRepository extends MongoRepository<ChatHistory, String> {
    List<ChatHistory> findAllByUserId(String userId);
    List<ChatHistory> findAllByChatId(String chatId);
}