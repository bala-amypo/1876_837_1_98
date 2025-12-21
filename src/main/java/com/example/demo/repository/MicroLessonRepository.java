// // package com.example.demo.repository;

// // import com.example.demo.model.MicroLesson;
// // import org.springframework.data.jpa.repository.JpaRepository;
// // import org.springframework.stereotype.Repository;

// // import java.util.List;

// // @Repository
// // public interface MicroLessonRepository extends JpaRepository<MicroLesson, Long> {

// //     // Simple filter — you can expand with @Query if needed
// //     List<MicroLesson> findByTagsContainingAndDifficultyAndContentType(
// //             String tags, String difficulty, String contentType);

// //     List<MicroLesson> findByCourseId(Long courseId);
// // }
// package com.example.demo.repository;

// import com.example.demo.model.MicroLesson;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.List;

// public interface MicroLessonRepository extends JpaRepository<MicroLesson, Long> {

//     List<MicroLesson> findByTagsContainingAndDifficultyAndContentType(String tags, String difficulty, String contentType);

// }
package com.example.demo.repository;

import com.example.demo.model.MicroLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MicroLessonRepository extends JpaRepository<MicroLesson, Long> {

    List<MicroLesson> findByTagsContainingAndDifficultyAndContentType(String tags, String difficulty, String contentType);
}
