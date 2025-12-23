// package com.example.demo.controller;

// import com.example.demo.model.Course;
// import com.example.demo.service.CourseService;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.List;

// @RestController
// @RequestMapping("/courses")
// @Tag(name = "Course Management", description = "APIs for creating and managing courses")
// public class CourseController {

//     private final CourseService courseService;

//     public CourseController(CourseService courseService) {
//         this.courseService = courseService;
//     }

   
//     @PostMapping
//     public ResponseEntity<Course> createCourse(@RequestParam Long instructorId,
//                                                @RequestBody Course course) {
//         Course created = courseService.createCourse(course, instructorId);
//         return ResponseEntity.ok(created);
//     }

//     @PutMapping("/{courseId}")
//     public ResponseEntity<Course> updateCourse(@PathVariable Long courseId,
//                                                @RequestBody Course course) {
//         Course updated = courseService.updateCourse(courseId, course);
//         return ResponseEntity.ok(updated);
//     }

 
//     @GetMapping("/instructor/{instructorId}")
//     public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Long instructorId) {
//         return ResponseEntity.ok(courseService.listCoursesByInstructor(instructorId));
//     }

   
//     @GetMapping("/{courseId}")
//     public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
//         return ResponseEntity.ok(courseService.getCourse(courseId));
//     }
// }
package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "Course Management")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @Operation(summary = "Create new course")
    public ResponseEntity<Course> createCourse(@RequestBody Course course, @RequestParam Long instructorId) {
        Course createdCourse = courseService.createCourse(course, instructorId);
        return ResponseEntity.ok(createdCourse);
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "Update existing course")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updatedCourse);
    }

    @GetMapping("/instructor/{instructorId}")
    @Operation(summary = "Get courses by instructor")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Long instructorId) {
        List<Course> courses = courseService.listCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "Get course by ID")
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }
}