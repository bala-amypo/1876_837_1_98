package com.example.demo.controller;

import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

 
    @PostMapping("/course/{courseId}")
    public MicroLesson addLesson(
            @PathVariable Long courseId,
            @RequestBody MicroLesson lesson) {
        return service.addLesson(courseId, lesson);
    }

    // 
    @PutMapping("/{lessonId}")
    public MicroLesson updateLesson(
            @PathVariable Long lessonId,
            @RequestBody MicroLesson lesson) {
        return service.updateLesson(lessonId, lesson);
    }

   
    @GetMapping("/{lessonId}")
    public MicroLesson getLesson(@PathVariable Long lessonId) {
        return service.getLesson(lessonId);
    }
}
