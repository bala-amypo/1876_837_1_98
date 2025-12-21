package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class RecommendationRequest {

    @NotNull
    private Long userId;

    private List<Long> courseIds; // optional: to filter recommendations

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<Long> getCourseIds() { return courseIds; }
    public void setCourseIds(List<Long> courseIds) { this.courseIds = courseIds; }
}
