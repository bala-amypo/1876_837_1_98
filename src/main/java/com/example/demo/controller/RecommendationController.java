// package com.example.demo.controller;

// import com.example.demo.model.Recommendation;
// import com.example.demo.service.RecommendationService;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/recommendations")
// @Tag(name = "Recommendations", description = "APIs for generating and retrieving content recommendations")
// public class RecommendationController {

//     private final RecommendationService service;

//     public RecommendationController(RecommendationService service) {
//         this.service = service;
//     }

//     //  Generate a recommendation
//     @PostMapping("/generate")
//     public ResponseEntity<Recommendation> generateRecommendation(
//             @RequestParam Long userId,
//             @RequestParam(required = false, defaultValue = "Default basis") String basisSnapshot,
//             @RequestParam(required = false, defaultValue = "0.95") double confidenceScore) {

//         Recommendation created = service.generateRecommendation(userId, basisSnapshot, confidenceScore);
//         return ResponseEntity.ok(created);
//     }

//     //  Get latest recommendation for user
//     @GetMapping("/latest")
//     public ResponseEntity<Recommendation> getLatest(@RequestParam Long userId) {
//         return ResponseEntity.ok(service.getLatest(userId));
//     }

//     //  Get all recommendations for a user
//     @GetMapping("/user/{userId}")
//     public ResponseEntity<List<Recommendation>> getUserRecommendations(@PathVariable Long userId) {
//         return ResponseEntity.ok(service.getByUser(userId));
//     }
// }
package com.example.demo.controller;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;
import com.example.demo.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
@Tag(name = "Recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate recommendation")
    public ResponseEntity<Recommendation> generateRecommendation(@RequestParam Long userId, 
                                                               @RequestBody RecommendationRequest request) {
        Recommendation recommendation = recommendationService.generateRecommendation(userId, request);
        return ResponseEntity.ok(recommendation);
    }

    @GetMapping("/latest")
    @Operation(summary = "Get latest recommendation")
    public ResponseEntity<Recommendation> getLatestRecommendation(@RequestParam Long userId) {
        Recommendation recommendation = recommendationService.getLatestRecommendation(userId);
        return ResponseEntity.ok(recommendation);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get recommendations for user")
    public ResponseEntity<List<Recommendation>> getRecommendations(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        
        if (from == null) from = LocalDate.now().minusMonths(1);
        if (to == null) to = LocalDate.now();
        
        List<Recommendation> recommendations = recommendationService.getRecommendations(userId, from, to);
        return ResponseEntity.ok(recommendations);
    }
}