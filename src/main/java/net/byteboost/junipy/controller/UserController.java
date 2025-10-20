package net.byteboost.junipy.controller;

import org.springframework.web.bind.annotation.*;
import net.byteboost.junipy.model.User;
import net.byteboost.junipy.model.UserProfile;
import net.byteboost.junipy.security.JwtUtil;
import net.byteboost.junipy.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private JwtUtil jwtUtils;
    private final IUserService userService;

    public UserController(IUserService userService, JwtUtil jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public List<User> all() { return userService.getAllUsers(); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) { userService.deleteUser(id); }

    @GetMapping("/profile-data")
    public Object getProfileData(@RequestHeader("Authorization") String authHeader) {
        String jwtoken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtoken);
        return userService.getUserProfile(userId);
    }
    
    @PostMapping("/profile-data")
    public Object upsertProfileData(@RequestHeader("Authorization") String authHeader, @RequestBody UserProfile profile) {
        String jwtoken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtoken);
        return userService.upsertUserProfile(userId, profile);
    }
}
