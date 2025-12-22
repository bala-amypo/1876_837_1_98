package com.example.demo.controller;

import com.example.demo.model.Recommendation;
import com.example.demo.service.RecommendationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@Tag(name = "Recommendations", description = "APIs for generating and retrieving content recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    //  Generate a recommendation
    @PostMapping("/generate")
    public ResponseEntity<Recommendation> generateRecommendation(
            @RequestParam Long userId,
            @RequestParam(required = false, defaultValue = "Default basis") String basisSnapshot,
            @RequestParam(required = false, defaultValue = "0.95") double confidenceScore) {

        Recommendation created = service.generateRecommendation(userId, basisSnapshot, confidenceScore);
        return ResponseEntity.ok(created);
    }

    //  Get latest recommendation for user
    @GetMapping("/latest")
    public ResponseEntity<Recommendation> getLatest(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getLatest(userId));
    }

    //  Get all recommendations for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendations(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }
}
