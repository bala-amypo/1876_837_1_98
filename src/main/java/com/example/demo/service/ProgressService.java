package com.example.demo.service;

import com.example.demo.model.Progress;   // ✅ REQUIRED
import java.util.List;

public interface ProgressService {

    Progress createProgress(Long userId, Long lessonId);

    Progress updateProgress(Long progressId, Double completion);

    List<Progress> getUserProgress(Long userId);
}
