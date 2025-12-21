package com.example.demo.controller;

import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService service;

    public ProgressController(ProgressService service) {
        this.service = service;
    }

    // ✅ POST – record or update progress
    @PostMapping("/{lessonId}")
    public Progress saveProgress(
            @PathVariable Long lessonId,
            @RequestBody Progress progress) {
        return service.saveProgress(lessonId, progress);
    }

    // ✅ GET – get user progress for lesson
    @GetMapping("/lesson/{lessonId}/user/{userId}")
    public Progress getProgress(
            @PathVariable Long lessonId,
            @PathVariable Long userId) {
        return service.getProgress(lessonId, userId);
    }
}
