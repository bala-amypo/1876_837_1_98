// package com.example.demo.service.impl;

// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.exception.ValidationException;
// import com.example.demo.model.Course;
// import com.example.demo.model.MicroLesson;
// import com.example.demo.model.User;
// import com.example.demo.repository.CourseRepository;
// import com.example.demo.repository.MicroLessonRepository;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.service.CourseService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDate;
// import java.util.List;

// @Service
// @Transactional
// public class CourseServiceImpl implements CourseService {

//     private final CourseRepository courseRepository;
//     private final UserRepository userRepository;
//     private final MicroLessonRepository lessonRepository;

//     public CourseServiceImpl(CourseRepository courseRepository,
//                              UserRepository userRepository,
//                              MicroLessonRepository lessonRepository) {
//         this.courseRepository = courseRepository;
//         this.userRepository = userRepository;
//         this.lessonRepository = lessonRepository;
//     }

//     @Override
//     public Course createCourse(Course course, Long instructorId) {
//         // ✅ 1. Fetch instructor
//         User instructor = userRepository.findById(instructorId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

//         // ✅ 2. Role validation
//         if (!"INSTRUCTOR".equalsIgnoreCase(instructor.getRole()) &&
//             !"ADMIN".equalsIgnoreCase(instructor.getRole())) {
//             throw new ValidationException("User is not authorized to create courses");
//         }

//         // ✅ 3. Prevent duplicate course titles per instructor
//         if (courseRepository.existsByTitleAndInstructorId(course.getTitle(), instructorId)) {
//             throw new ValidationException("Course title already exists for this instructor");
//         }

//         // ✅ 4. Link instructor
//         course.setInstructor(instructor);

//         // ✅ 5. Save course first to generate ID
//         Course created = courseRepository.save(course);

//         // ✅ 6. Save lessons (if included)
//         if (course.getLessons() != null && !course.getLessons().isEmpty()) {
//             for (MicroLesson lesson : course.getLessons()) {
//                 lesson.setCourse(created); // link to saved course

//                 // Set default values to avoid NULLs
//                 if (lesson.getContentType() == null || lesson.getContentType().trim().isEmpty())
//                     lesson.setContentType("VIDEO");

//                 if (lesson.getDifficulty() == null || lesson.getDifficulty().trim().isEmpty())
//                     lesson.setDifficulty("BEGINNER");

//                 if (lesson.getTags() == null) lesson.setTags("");

//                 if (lesson.getPublishDate() == null) lesson.setPublishDate(LocalDate.now());

//                 // Validate title and duration
//                 if (lesson.getTitle() == null || lesson.getTitle().trim().isEmpty())
//                     throw new ValidationException("Lesson title cannot be null or empty");

//                 if (lesson.getDurationMinutes() == null ||
//                         lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15)
//                     throw new ValidationException("Lesson duration must be between 1 and 15 minutes");
//             }

//             lessonRepository.saveAll(course.getLessons());
//         }

//         return created;
//     }

//     @Override
//     public Course updateCourse(Long courseId, Course updatedCourse) {
//         Course course = courseRepository.findById(courseId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

//         course.setTitle(updatedCourse.getTitle());
//         course.setDescription(updatedCourse.getDescription());
//         course.setCategory(updatedCourse.getCategory());

//         return courseRepository.save(course);
//     }

//     @Override
//     public List<Course> listCoursesByInstructor(Long instructorId) {
//         return courseRepository.findByInstructorId(instructorId);
//     }

//     @Override
//     public Course getCourse(Long courseId) {
//         return courseRepository.findById(courseId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
//     }
// }
// NEW
// package com.example.demo.service.impl;

// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.model.Course;
// import com.example.demo.model.User;
// import com.example.demo.repository.CourseRepository;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.service.CourseService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.List;

// @Service
// @Transactional
// public class CourseServiceImpl implements CourseService {

//     private final CourseRepository courseRepository;
//     private final UserRepository userRepository;

//     public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
//         this.courseRepository = courseRepository;
//         this.userRepository = userRepository;
//     }

//     @Override
//     public Course createCourse(Course course, Long instructorId) {
//         User instructor = userRepository.findById(instructorId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        
//         if (!instructor.getRole().equals("INSTRUCTOR") && !instructor.getRole().equals("ADMIN")) {
//             throw new IllegalArgumentException("User must be INSTRUCTOR or ADMIN to create courses");
//         }
        
//         if (courseRepository.existsByTitleAndInstructorId(course.getTitle(), instructorId)) {
//             throw new IllegalArgumentException("Course with this title already exists for this instructor");
//         }
        
//         course.setInstructor(instructor);
//         return courseRepository.save(course);
//     }

//     @Override
//     public Course updateCourse(Long courseId, Course course) {
//         Course existingCourse = courseRepository.findById(courseId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        
//         existingCourse.setTitle(course.getTitle());
//         existingCourse.setDescription(course.getDescription());
//         existingCourse.setCategory(course.getCategory());
        
//         return courseRepository.save(existingCourse);
//     }

//     @Override
//     public List<Course> listCoursesByInstructor(Long instructorId) {
//         return courseRepository.findByInstructorId(instructorId);
//     }

//     @Override
//     public Course getCourse(Long courseId) {
//         return courseRepository.findById(courseId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
//     }
// }
package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Course createCourse(Course course, Long instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        
        if (!instructor.getRole().equals("INSTRUCTOR") && !instructor.getRole().equals("ADMIN")) {
            throw new IllegalArgumentException("User must be INSTRUCTOR or ADMIN to create courses");
        }
        
        if (courseRepository.existsByTitleAndInstructorId(course.getTitle(), instructorId)) {
            throw new IllegalArgumentException("Course with this title already exists for this instructor");
        }
        
        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long courseId, Course course) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        
        if (course.getTitle() != null) {
            existingCourse.setTitle(course.getTitle());
        }
        if (course.getDescription() != null) {
            existingCourse.setDescription(course.getDescription());
        }
        if (course.getCategory() != null) {
            existingCourse.setCategory(course.getCategory());
        }
        
        return courseRepository.save(existingCourse);
    }

    @Override
    public List<Course> listCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }
}