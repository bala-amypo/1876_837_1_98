// package com.example.demo.controller;

// import com.example.demo.model.MicroLesson;
// import com.example.demo.service.LessonService;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.List;

// @RestController
// @RequestMapping("/lessons")
// @Tag(name = "Lesson Management", description = "APIs for adding and retrieving micro-lessons")
// public class LessonController {

//     private final LessonService lessonService;

//     public LessonController(LessonService lessonService) {
//         this.lessonService = lessonService;
//     }

//     @PostMapping("/course/{courseId}")
//     public ResponseEntity<MicroLesson> addLesson(@PathVariable Long courseId,
//                                                  @RequestBody MicroLesson lesson) {
//         MicroLesson created = lessonService.addLesson(courseId, lesson);
//         return ResponseEntity.ok(created);
//     }

//     @PutMapping("/{lessonId}")
//     public ResponseEntity<MicroLesson> updateLesson(@PathVariable Long lessonId,
//                                                     @RequestBody MicroLesson lesson) {
//         MicroLesson updated = lessonService.updateLesson(lessonId, lesson);
//         return ResponseEntity.ok(updated);
//     }

//     @GetMapping("/search")
//     public ResponseEntity<List<MicroLesson>> searchLessons(@RequestParam(required = false) String tags,
//                                                            @RequestParam(required = false) String difficulty,
//                                                            @RequestParam(required = false) String contentType) {
//         return ResponseEntity.ok(lessonService.findLessonsByFilters(tags, difficulty, contentType));
//     }

//     @GetMapping("/{lessonId}")
//     public ResponseEntity<MicroLesson> getLesson(@PathVariable Long lessonId) {
//         return ResponseEntity.ok(lessonService.getLesson(lessonId));
//     }
// }
package com.example.demo.controller;

import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/lessons")
@Tag(name = "Lesson Management", description = "APIs for adding and retrieving micro-lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/course/{courseId}")
    public ResponseEntity<MicroLesson> addLesson(@PathVariable Long courseId,
                                                 @RequestBody MicroLesson lesson) {
        MicroLesson created = lessonService.addLesson(courseId, lesson);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<MicroLesson> updateLesson(@PathVariable Long lessonId,
                                                    @RequestBody MicroLesson lesson) {
        MicroLesson updated = lessonService.updateLesson(lessonId, lesson);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MicroLesson>> searchLessons(@RequestParam(required = false) String tags,
                                                           @RequestParam(required = false) String difficulty,
                                                           @RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(lessonService.findLessonsByFilters(tags, difficulty, contentType));
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<MicroLesson> getLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.getLesson(lessonId));
    }
}
