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

    @PostMapping("/{lessonId}")
    public ResponseEntity<Progress> recordProgress(@RequestParam Long userId,
                                                   @PathVariable Long lessonId,
                                                   @RequestBody Progress progress) {
        Progress saved = progressService.recordProgress(userId, lessonId, progress);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<Progress> getProgress(@RequestParam Long userId,
                                                @PathVariable Long lessonId) {
        return ResponseEntity.ok(progressService.getProgress(userId, lessonId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Progress>> getUserProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getUserProgress(userId));
    }
}
