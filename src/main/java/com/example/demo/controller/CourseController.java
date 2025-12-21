package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    public Course createCourse(@RequestParam Long instructorId, @RequestBody Course course) {
        return courseService.createCourse(course, instructorId);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

    @GetMapping("/instructor/{instructorId}")
    public List<Course> listCourses(@PathVariable Long instructorId) {
        return courseService.listCoursesByInstructor(instructorId);
    }
}
