// package com.example.demo.service;

// import com.example.demo.model.Recommendation;
// import java.time.LocalDate;
// import java.util.List;

// public interface RecommendationService {

//     Recommendation generateRecommendation(Long userId);

//     Recommendation getLatestRecommendation(Long userId);

//     List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to);
// }
package com.example.demo.service;

import com.example.demo.model.Recommendation;

public interface RecommendationService {

    Recommendation generateRecommendation(Long userId,
                                          String difficulty,
                                          String contentType,
                                          String tags);
}

