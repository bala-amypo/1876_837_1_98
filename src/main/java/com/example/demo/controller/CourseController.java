package com.example.demo.controller;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Create a new course along with lessons
    @PostMapping
    public ResponseEntity<Course> createCourse(
            @RequestParam Long instructorId,
            @RequestBody Course course) {
        try {
            Course created = courseService.createCourse(course, instructorId);
            return ResponseEntity.ok(created);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get course by ID
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }

    // Get all courses by instructor
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Long instructorId) {
        return ResponseEntity.ok(courseService.listCoursesByInstructor(instructorId));
    }

    // Update a course
    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course updatedCourse) {
        return ResponseEntity.ok(courseService.updateCourse(courseId, updatedCourse));
    }
}
