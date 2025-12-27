

package com.example.demo.controller;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;
import com.example.demo.service.impl.RecommendationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
@Tag(name = "5. Recommendations")
public class RecommendationController {
    private final RecommendationServiceImpl recommendationService;

    @PostMapping("/generate")
    @Operation(summary = "Generate a new micro-learning recommendation")
    public ResponseEntity<Recommendation> generate(@RequestParam Long userId, @RequestBody RecommendationRequest req) {
        return ResponseEntity.ok(recommendationService.generateRecommendation(userId, req));
    }

    @GetMapping("/latest")
    @Operation(summary = "Get the latest recommendation for the user")
    public ResponseEntity<Recommendation> getLatest(@RequestParam Long userId) {
        return ResponseEntity.ok(recommendationService.getLatestRecommendation(userId));
    }
}