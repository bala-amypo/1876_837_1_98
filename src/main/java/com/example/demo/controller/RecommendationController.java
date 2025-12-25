package com.example.demo.controller;

import com.example.demo.model.Recommendation;
import com.example.demo.service.impl.RecommendationServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
@Tag(name = "Recommendations")
public class RecommendationController {

    private final RecommendationServiceImpl recommendationService;

    @GetMapping("/latest")
    public ResponseEntity<Recommendation> getLatestRecommendation(@RequestParam Long userId) {
        return ResponseEntity.ok(recommendationService.getLatestRecommendation(userId));
    }
}