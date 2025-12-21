package com.example.demo.service;

import com.example.demo.model.Progress;
import java.util.List;

public interface ProgressService {

    Progress recordProgress(Long lessonId, Progress progress);

    List<Progress> getProgressByUser(Long userId);

    List<Progress> getProgressByLesson(Long lessonId);
}
