package com.example.demo.controller;

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

    // Create course with lessons
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course, 
                                               @RequestParam Long instructorId) {
        Course created = courseService.createCourse(course, instructorId);
        return ResponseEntity.ok(created);
    }

    // Get all courses by instructor
    @GetMapping("/instructor/{id}")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Long id) {
        List<Course> courses = courseService.listCoursesByInstructor(id);
        return ResponseEntity.ok(courses);
    }

    // Get course by id
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        Course course = courseService.getCourse(id);
        return ResponseEntity.ok(course);
    }
}
