
// package com.example.demo.dto;

// import lombok.Data;

// @Data
// public class RecommendationRequest {

//     private String difficulty;
//     private String contentType;
//     private String tags;
// }

package com.example.demo.dto;

import lombok.Data;

@Data
public class RecommendationRequest {
    private String tags;
    private String difficulty;
    private String contentType;
    private Integer limit;
    private String preferredLearningStyle;
}