package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    // ✅ POST – create course
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return service.createCourse(course);
    }

    // ✅ PUT – update course
    @PutMapping("/{courseId}")
    public Course updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course) {
        return service.updateCourse(courseId, course);
    }

    // ✅ GET – get course by id
    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable Long courseId) {
        return service.getCourse(courseId);
    }

    // ✅ GET – instructor courses
    @GetMapping("/instructor/{instructorId}")
    public List<Course> getInstructorCourses(@PathVariable String instructorId) {
        return service.getInstructorCourses(instructorId);
    }
}
