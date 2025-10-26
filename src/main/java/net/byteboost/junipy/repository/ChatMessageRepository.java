package net.byteboost.junipy.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import net.byteboost.junipy.model.ChatMessageDocument;

public interface ChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {
    List<ChatMessageDocument> findByUserIdOrderByCreatedAtAsc(String userId);
    List<ChatMessageDocument> findByChatIdOrderByCreatedAtAsc(String chatId);
}
