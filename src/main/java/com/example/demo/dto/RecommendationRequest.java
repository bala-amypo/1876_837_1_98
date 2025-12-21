// package com.example.demo.dto;

// import lombok.*;
// import java.math.BigDecimal;
// import java.util.List;

// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// public class RecommendationRequest {
//     private Long userId;
//     private List<Long> completedLessonIds;
//     private String preferredCategory;
//     private String difficultyLevel;
//     private BigDecimal confidenceThreshold;
// }
package com.example.demo.dto;

import lombok.Data;

@Data
public class RecommendationRequest {

    private String difficulty;
    private String contentType;
    private String tags;
}

