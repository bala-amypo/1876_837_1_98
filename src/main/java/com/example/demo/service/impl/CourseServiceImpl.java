package com.example.demo.service.impl;

import com.example.demo.dto.CourseDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.Lesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public Course createCourse(CourseDTO courseDTO, Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setCategory(courseDTO.getCategory());
        course.setInstructor(instructor);

        if (courseDTO.getLessons() != null) {
            course.setLessons(courseDTO.getLessons().stream().map(lessonDTO -> {
                Lesson lesson = new Lesson();
                lesson.setTitle(lessonDTO.getTitle());
                lesson.setDurationMinutes(lessonDTO.getDurationMinutes());
                lesson.setContentType(lessonDTO.getContentType());
                lesson.setDifficulty(lessonDTO.getDifficulty());
                lesson.setTags(lessonDTO.getTags());
                lesson.setCourse(course);
                return lesson;
            }).collect(Collectors.toList()));
        }

        return courseRepository.save(course);
    }

    @Override
    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Course updateCourse(Long id, CourseDTO courseDTO) {
        Course course = getCourse(id);
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setCategory(courseDTO.getCategory());
        // For lessons, you can add update logic as needed
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = getCourse(id);
        courseRepository.delete(course);
    }
}
