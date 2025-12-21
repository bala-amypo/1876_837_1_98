package com.example.demo.service;

import com.example.demo.model.Course;
import java.util.List;

public interface CourseService {

    // Create course (requires instructorId)
    Course createCourse(Course course, Long instructorId);

    // Get all courses
    List<Course> getAllCourses();

    // Get course by ID
    Course getCourseById(Long id);

    // Delete course
    void deleteCourse(Long id);
}
