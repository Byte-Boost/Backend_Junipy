package net.byteboost.junipy.repository;

import net.byteboost.junipy.model.UserDietAnalysis;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDietAnalysisRepository extends MongoRepository<UserDietAnalysis, String> {
    UserDietAnalysis findByDietId(String dietId);
    List<UserDietAnalysis> findByNutritionistUserId(String nutritionistUserId);
    List<UserDietAnalysis> findBySubjectUserId(String subjectUserId);
    List<UserDietAnalysis> findByStatus(net.byteboost.junipy.dto.ReviewStatusEnum status);
}
