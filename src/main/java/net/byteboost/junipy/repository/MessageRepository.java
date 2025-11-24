package net.byteboost.junipy.repository;

import net.byteboost.junipy.model.Message;

//import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    //public List<Message> findAllBySenderId(String senderId);
}
