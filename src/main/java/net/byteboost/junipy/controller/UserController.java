package net.byteboost.junipy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import net.byteboost.junipy.dto.UserDailyDietCreateDto;
import net.byteboost.junipy.model.User;
import net.byteboost.junipy.model.UserDailyDiet;
import net.byteboost.junipy.model.UserDietAnalysis;
import net.byteboost.junipy.model.UserProfile;
import net.byteboost.junipy.security.JwtUtil;
import net.byteboost.junipy.service.IDietService;
import net.byteboost.junipy.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private JwtUtil jwtUtils;
    private final IUserService userService;
    private final IDietService dietService;

    public UserController(IUserService userService, JwtUtil jwtUtils, IDietService dietService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.dietService = dietService;
    }

    @GetMapping
    public ResponseEntity<List<User>> all() { return ResponseEntity.ok(userService.getAllUsers()); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { userService.deleteUser(id); return ResponseEntity.noContent().build(); }

    @PatchMapping("/setNutritionist/{id}")
    public ResponseEntity<Void> setAsNutritionist(@PathVariable String id) {
        userService.setUserAsNutritionist(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/profile-data")
    public ResponseEntity<UserProfile> getProfileData(@RequestHeader("Authorization") String authHeader) {
        String jwtoken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtoken);
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }
    
    @PostMapping("/profile-data")
    public ResponseEntity<Void> upsertProfileData(@RequestHeader("Authorization") String authHeader, @RequestBody UserProfile profile) {
        String jwtoken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtoken);
        userService.upsertUserProfile(userId, profile);
        return ResponseEntity.status(201).build(); 
    }

    @PostMapping("/daily-diet")
    public ResponseEntity<Void> logDailyDiet(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody UserDailyDietCreateDto dailyDiet) {
        String jwtoken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtoken);
        dietService.createUserDailyDiet(userId, dailyDiet);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/daily-diet")
    public ResponseEntity<List<UserDailyDiet>> getUserDailyDiet(@RequestHeader("Authorization") String authHeader) {
        String jwtoken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtoken);
        return ResponseEntity.ok(dietService.getUserDailyDiet(userId));
    }

    @GetMapping("/diet-analysis-requests")
    public ResponseEntity<List<UserDietAnalysis>> getDietAnalysisRequests(@RequestHeader("Authorization") String authHeader) {
        String jwtoken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtoken);
        return ResponseEntity.ok(dietService.getUserDietAnalysesByUser(userId));
    }

    @PostMapping("/request-diet-analysis/{dietId}")
    public ResponseEntity<?> requestDietAnalysis(@RequestHeader("Authorization") String authHeader, @PathVariable String dietId) {
        String jwtoken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtoken);
        try{
            dietService.requestDietAnalysis(userId, dietId);
            return ResponseEntity.status(201).build();
        } catch (IllegalArgumentException e){            
            return ResponseEntity.status(400).body("Error while requesting analysis: " + e.getMessage());
        }
    }
}
