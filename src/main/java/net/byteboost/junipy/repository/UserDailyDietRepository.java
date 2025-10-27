package net.byteboost.junipy.repository;

import net.byteboost.junipy.dto.DayOfWeekEnum;
import net.byteboost.junipy.model.UserDailyDiet;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDailyDietRepository extends MongoRepository<UserDailyDiet, String> {
    UserDailyDiet findByUserIdAndDayOfWeek(String userId, DayOfWeekEnum dayOfWeek);
    List<UserDailyDiet> findByUserId(String userId);
}
