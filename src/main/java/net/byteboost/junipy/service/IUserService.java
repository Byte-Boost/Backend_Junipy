package net.byteboost.junipy.service;

import net.byteboost.junipy.model.User;
import net.byteboost.junipy.model.UserProfile;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User getUserById(String id);
    User getUserByEmail(String email);
    User createUser(User user);
    User updateUser(String id, User user);
    void deleteUser(String id);

    UserProfile getUserProfile(String userId);
    UserProfile upsertUserProfile(String userId, UserProfile profile);
}
