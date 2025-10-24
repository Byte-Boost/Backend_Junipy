package net.byteboost.junipy.service;

import net.byteboost.junipy.dto.ReviewStatusEnum;
import net.byteboost.junipy.dto.UserDailyDietCreateDto;
import net.byteboost.junipy.model.User;
import net.byteboost.junipy.model.UserDailyDiet;
import net.byteboost.junipy.model.UserDietAnalysis;
import net.byteboost.junipy.model.UserProfile;
import net.byteboost.junipy.repository.UserDailyDietRepository;
import net.byteboost.junipy.repository.UserDietAnalysisRepository;
import net.byteboost.junipy.repository.UserProfileRepository;
import net.byteboost.junipy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietService implements IDietService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserDailyDietRepository userDailyDietRepository;
    private final UserDietAnalysisRepository userDietAnalysisRepository;

    public DietService(UserRepository userRepository, UserProfileRepository userProfileRepository, UserDailyDietRepository userDailyDietRepository, UserDietAnalysisRepository userDietAnalysisRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userDailyDietRepository = userDailyDietRepository;
        this.userDietAnalysisRepository = userDietAnalysisRepository;
    }

    @Override
    public UserDailyDiet createUserDailyDiet(String userId, UserDailyDietCreateDto diet) {
        diet.setUserId(userId);
        UserDailyDiet existingDiet = userDailyDietRepository.findByUserIdAndDayOfWeek(userId, diet.getDayOfWeek());
        
        if (existingDiet != null) {
            existingDiet.setMarkdownDiet(diet.getMarkdownDiet());
            return userDailyDietRepository.save(existingDiet);
        } else {
            UserDailyDiet newDiet = new UserDailyDiet();
            newDiet.setUserId(diet.getUserId());
            newDiet.setDayOfWeek(diet.getDayOfWeek());
            newDiet.setMarkdownDiet(diet.getMarkdownDiet());
            return userDailyDietRepository.save(newDiet);
        }
    }
    
    @Override
    public List<UserDailyDiet> getUserDailyDiet(String userId) {
        return userDailyDietRepository.findByUserId(userId);
    }


    // Diet Analysis Methods
    @Override
    public UserDietAnalysis getUserDietAnalysis(String dietId){
        return userDietAnalysisRepository.findByDietId(dietId);
    }
    
    @Override
    public List<UserDietAnalysis> getUserDietAnalysesByNutritionist(String nutritionistUserId){
        return userDietAnalysisRepository.findByNutritionistUserId(nutritionistUserId);
    }
    
    @Override
    public List<UserDietAnalysis> getUserDietAnalysesByUser(String userId){
        return userDietAnalysisRepository.findBySubjectUserId(userId);
    }
    
    @Override
    public List<UserDietAnalysis> getUserDietAnalysesByStatus(ReviewStatusEnum status){
        return userDietAnalysisRepository.findByStatus(status);
    }

    @Override
    public UserDietAnalysis requestDietAnalysis(String userId, String dietId) {
        UserDailyDiet diet = userDailyDietRepository.findById(dietId)
            .orElseThrow(() -> new IllegalArgumentException("No daily diet found for id: " + dietId));
        if (!diet.getUserId().equals(userId)) 
            throw new IllegalArgumentException("User does not have permission to request analysis for this diet");

        UserDietAnalysis existingAnalysis = userDietAnalysisRepository.findByDietId(dietId);
        if (existingAnalysis != null)
            throw new IllegalArgumentException("Diet analysis request already exists for dietId: " + dietId);

        UserDietAnalysis newAnalysis = new UserDietAnalysis(dietId, userId);
        newAnalysis.setStatus(ReviewStatusEnum.IN_PROGRESS);

        return userDietAnalysisRepository.save(newAnalysis);
    }

    @Override
    public UserDietAnalysis submitDietAnalysis(String nutritionistUserId, String dietId, ReviewStatusEnum status) {
        UserDietAnalysis analysis = userDietAnalysisRepository.findByDietId(dietId);

        if (analysis == null)
            throw new IllegalArgumentException("No diet analysis found for dietId: " + dietId);
        if (status != ReviewStatusEnum.APPROVED && status != ReviewStatusEnum.DECLINED)
            throw new IllegalStateException("Cannot mark diet analysis as " + status);

        analysis.setStatus(status);
        analysis.setNutritionistUserId(nutritionistUserId);

        return userDietAnalysisRepository.save(analysis);
    }

    @Override
    public UserDietAnalysis submitDietAnalysis(String nutritionistUserId, String dietId, ReviewStatusEnum status, String comments) {
        UserDietAnalysis analysis = userDietAnalysisRepository.findByDietId(dietId);

        if (analysis == null)
            throw new IllegalArgumentException("No diet analysis found for dietId: " + dietId);
        if (status == ReviewStatusEnum.IN_PROGRESS)
            throw new IllegalStateException("Cannot mark diet analysis as " + status);

        analysis.setStatus(status);
        analysis.setNutritionistUserId(nutritionistUserId);
        analysis.setComments(comments);

        return userDietAnalysisRepository.save(analysis);
    }

}
