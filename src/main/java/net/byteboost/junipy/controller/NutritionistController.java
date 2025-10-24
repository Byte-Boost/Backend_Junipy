package net.byteboost.junipy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.byteboost.junipy.dto.ReviewStatusEnum;
import net.byteboost.junipy.model.UserDietAnalysis;
import net.byteboost.junipy.security.JwtUtil;
import net.byteboost.junipy.service.IDietService;

@RestController
@RequestMapping("/nutritionist")
public class NutritionistController {

    private final IDietService dietService;
    private JwtUtil jwtUtils;

    public NutritionistController(IDietService dietService, JwtUtil jwtUtils) {
        this.dietService = dietService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/diet-analyses/pending")
    public ResponseEntity<List<UserDietAnalysis>> getPendingDietAnalyses() {
        return ResponseEntity.ok().body(
            dietService.getUserDietAnalysesByStatus(ReviewStatusEnum.IN_PROGRESS)
        );
    }
    
    @PostMapping("/diet-analyses/{dietId}/submit")
    public ResponseEntity<?> submitDietAnalysis(@RequestHeader("Authorization") String authHeader, @PathVariable String dietId, @RequestBody UserDietAnalysis analysis) {
        String jwtoken = authHeader.replace("Bearer ", "");
        String userId = jwtUtils.extractUserId(jwtoken);
        try{
            if (analysis.getComments() == null){
                dietService.submitDietAnalysis(userId, dietId, analysis.getStatus());
            } else {
                dietService.submitDietAnalysis(userId, dietId, analysis.getStatus(), analysis.getComments());
            }
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Error submitting diet analysis: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }

    }
}
