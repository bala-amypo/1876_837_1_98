package com.example.demo.controller;

import com.example.demo.model.MicroLesson;
import com.example.demo.service.impl.LessonServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@Tag(name = "Lesson Management")
public class LessonController {

    private final LessonServiceImpl lessonService;

    @PostMapping("/course/{courseId}")
    public ResponseEntity<MicroLesson> addLesson(@PathVariable Long courseId, @RequestBody MicroLesson lesson) {
        return ResponseEntity.ok(lessonService.addLesson(courseId, lesson));
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<MicroLesson> updateLesson(@PathVariable Long lessonId, @RequestBody MicroLesson lesson) {
        return ResponseEntity.ok(lessonService.updateLesson(lessonId, lesson));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MicroLesson>> searchLessons(
            @RequestParam String tags,
            @RequestParam String difficulty,
            @RequestParam String contentType) {
        return ResponseEntity.ok(lessonService.findLessonsByFilters(tags, difficulty, contentType));
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<MicroLesson> getLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.getLesson(lessonId));
    }
}