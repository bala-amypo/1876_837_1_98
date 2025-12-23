// package com.example.demo.controller;

// import com.example.demo.model.MicroLesson;
// import com.example.demo.service.LessonService;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/lessons")
// public class LessonController {

//     private final LessonService service;

//     public LessonController(LessonService service) {
//         this.service = service;
//     }

 
//     @PostMapping("/course/{courseId}")
//     public MicroLesson addLesson(
//             @PathVariable Long courseId,
//             @RequestBody MicroLesson lesson) {
//         return service.addLesson(courseId, lesson);
//     }

//     // 
//     @PutMapping("/{lessonId}")
//     public MicroLesson updateLesson(
//             @PathVariable Long lessonId,
//             @RequestBody MicroLesson lesson) {
//         return service.updateLesson(lessonId, lesson);
//     }

   
//     @GetMapping("/{lessonId}")
//     public MicroLesson getLesson(@PathVariable Long lessonId) {
//         return service.getLesson(lessonId);
//     }
// }
package com.example.demo.controller;

import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@Tag(name = "Lesson Management")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/course/{courseId}")
    @Operation(summary = "Add lesson to course")
    public ResponseEntity<MicroLesson> addLesson(@PathVariable Long courseId, @RequestBody MicroLesson lesson) {
        MicroLesson createdLesson = lessonService.addLesson(courseId, lesson);
        return ResponseEntity.ok(createdLesson);
    }

    @PutMapping("/{lessonId}")
    @Operation(summary = "Update lesson")
    public ResponseEntity<MicroLesson> updateLesson(@PathVariable Long lessonId, @RequestBody MicroLesson lesson) {
        MicroLesson updatedLesson = lessonService.updateLesson(lessonId, lesson);
        return ResponseEntity.ok(updatedLesson);
    }

    @GetMapping("/search")
    @Operation(summary = "Search lessons by filters")
    public ResponseEntity<List<MicroLesson>> searchLessons(
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String contentType) {
        List<MicroLesson> lessons = lessonService.findLessonsByFilters(tags, difficulty, contentType);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("/{lessonId}")
    @Operation(summary = "Get lesson by ID")
    public ResponseEntity<MicroLesson> getLesson(@PathVariable Long lessonId) {
        MicroLesson lesson = lessonService.getLesson(lessonId);
        return ResponseEntity.ok(lesson);
    }
}