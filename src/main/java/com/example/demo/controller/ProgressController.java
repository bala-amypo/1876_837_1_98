package com.example.demo.controller;

import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@Tag(name = "Progress Tracking", description = "APIs for tracking learner progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    //  Record progress for a user on a specific lesson
    @PostMapping("/{lessonId}")
    public ResponseEntity<Progress> recordProgress(@PathVariable Long lessonId,
                                                   @RequestBody Progress progress) {
        Progress created = progressService.recordProgress(lessonId, progress);
        return ResponseEntity.ok(created);
    }

    //  Get progress by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Progress>> getProgressByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getProgressByUser(userId));
    }

    //  Get progress by lesson ID
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<Progress>> getProgressByLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(progressService.getProgressByLesson(lessonId));
    }
}
