package com.example.demo.service;

import com.example.demo.model.Progress;
import java.util.List;

public interface ProgressService {
    Progress startLesson(Long userId, Long lessonId);
    Progress completeLesson(Long userId, Long lessonId);
    List<Progress> getUserProgress(Long userId);
}
