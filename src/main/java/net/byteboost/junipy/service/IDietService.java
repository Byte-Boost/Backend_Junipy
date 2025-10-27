package net.byteboost.junipy.service;

import net.byteboost.junipy.dto.ReviewStatusEnum;
import net.byteboost.junipy.dto.UserDailyDietCreateDto;
import net.byteboost.junipy.model.UserDailyDiet;
import net.byteboost.junipy.model.UserDietAnalysis;

import java.util.List;

public interface IDietService {
    UserDailyDiet createUserDailyDiet(String userId, UserDailyDietCreateDto diet);
    List<UserDailyDiet> getUserDailyDiet(String userId);

    UserDietAnalysis getUserDietAnalysis(String dietId);
    List<UserDietAnalysis> getUserDietAnalysesByNutritionist(String nutritionistUserId);
    List<UserDietAnalysis> getUserDietAnalysesByUser(String userId);
    List<UserDietAnalysis> getUserDietAnalysesByStatus(ReviewStatusEnum status);

    UserDietAnalysis requestDietAnalysis(String userId, String dietId);
    UserDietAnalysis submitDietAnalysis(String nutritionistUserId, String dietId, ReviewStatusEnum status);
    UserDietAnalysis submitDietAnalysis(String nutritionistUserId, String dietId, ReviewStatusEnum status, String comments);
}
