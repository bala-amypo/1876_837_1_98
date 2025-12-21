package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "Course Management", description = "APIs for creating and managing courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ✅ Create a new course
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestParam Long instructorId,
                                               @RequestBody Course course) {
        Course created = courseService.createCourse(course, instructorId);
        return ResponseEntity.ok(created);
    }

    // ✅ Update an existing course
    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId,
                                               @RequestBody Course course) {
        Course updated = courseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updated);
    }

    // ✅ Get all courses by instructor ID
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Long instructorId) {
        return ResponseEntity.ok(courseService.listCoursesByInstructor(instructorId));
    }

    // ✅ Get a single course
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }
}
