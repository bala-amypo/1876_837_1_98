package com.example.demo.controller;

import com.example.demo.model.MicroLesson;
import com.example.demo.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<MicroLesson> getRecommendations(
            @RequestParam Long userId,
            @RequestParam String difficulty,
            @RequestParam String contentType
    ) {
        return recommendationService.recommendLessons(
                userId, difficulty, contentType
        );
    }
}
