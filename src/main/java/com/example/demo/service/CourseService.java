package com.example.demo.service;

import com.example.demo.model.Course;
import java.util.List;

public interface CourseService {
    Course createCourse(Course course, Long instructorId);
    List<Course> getAllCourses();
    Course getCourseById(Long id);
    void deleteCourse(Long id);
}
