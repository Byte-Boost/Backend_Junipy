package net.byteboost.junipy.repository;

import net.byteboost.junipy.model.Chat;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
    public List<Chat> findByUserId(String userId);
}