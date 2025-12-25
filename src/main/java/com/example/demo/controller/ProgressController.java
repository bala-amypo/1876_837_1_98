package com.example.demo.controller;

import com.example.demo.model.Progress;
import com.example.demo.service.impl.ProgressServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
@Tag(name = "Progress Tracking")
public class ProgressController {

    private final ProgressServiceImpl progressService;

    @PostMapping("/{lessonId}")
    public ResponseEntity<Progress> recordProgress(
            @PathVariable Long lessonId,
            @RequestParam Long userId,
            @RequestBody Progress progress) {
        return ResponseEntity.ok(progressService.recordProgress(userId, lessonId, progress));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Progress>> getUserProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getUserProgress(userId));
    }
}