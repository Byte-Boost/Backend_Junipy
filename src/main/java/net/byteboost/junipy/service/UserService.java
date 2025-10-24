package net.byteboost.junipy.service;

import net.byteboost.junipy.model.User;
import net.byteboost.junipy.model.UserDailyDiet;
import net.byteboost.junipy.model.UserProfile;
import net.byteboost.junipy.repository.UserDailyDietRepository;
import net.byteboost.junipy.repository.UserProfileRepository;
import net.byteboost.junipy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserDailyDietRepository userDailyDietRepository;

    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository, UserDailyDietRepository userDailyDietRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userDailyDietRepository = userDailyDietRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserProfile getUserProfile(String userId){
        return userProfileRepository.findByUserId(userId);
    }

    @Override
    public UserProfile upsertUserProfile(String userId, UserProfile profile){
        UserProfile existingProfile = userProfileRepository.findByUserId(userId);
        if(existingProfile != null){
            profile.setId(existingProfile.getId());
        }
        profile.setUserId(userId);
        return userProfileRepository.save(profile);
    }

    @Override
    public UserDailyDiet createUserDailyDiet(String userId, UserDailyDiet diet) {
        diet.setUserId(userId);
        UserDailyDiet existingDiet = userDailyDietRepository.findByUserIdAndDayOfWeek(userId, diet.getDayOfWeek());
        if (existingDiet != null) {
            diet.setId(existingDiet.getId());
        }
        return userDailyDietRepository.save(diet);
    }
    
    @Override
    public List<UserDailyDiet> getUserDailyDiet(String userId) {
        return userDailyDietRepository.findByUserId(userId);
    }

}
