// package com.example.demo.service.impl;

// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.model.MicroLesson;
// import com.example.demo.model.Progress;
// import com.example.demo.model.User;
// import com.example.demo.repository.MicroLessonRepository;
// import com.example.demo.repository.ProgressRepository;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.service.ProgressService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// @Transactional
// public class ProgressServiceImpl implements ProgressService {

//     private final ProgressRepository progressRepository;
//     private final UserRepository userRepository;
//     private final MicroLessonRepository microLessonRepository;

//     public ProgressServiceImpl(ProgressRepository progressRepository,
//                                UserRepository userRepository,
//                                MicroLessonRepository microLessonRepository) {
//         this.progressRepository = progressRepository;
//         this.userRepository = userRepository;
//         this.microLessonRepository = microLessonRepository;
//     }

//     @Override
//     public Progress recordProgress(Long lessonId, Progress progress) {
//         MicroLesson lesson = microLessonRepository.findById(lessonId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

//         User user = userRepository.findById(progress.getUser().getId())
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));

//         progress.setMicroLesson(lesson);
//         progress.setUser(user);
//         progress.setLastAccessedAt(LocalDateTime.now());

//         return progressRepository.save(progress);
//     }

//     @Override
//     public List<Progress> getProgressByUser(Long userId) {
//         return progressRepository.findAll().stream()
//                 .filter(p -> p.getUser().getId().equals(userId))
//                 .toList();
//     }

//     @Override
//     public List<Progress> getProgressByLesson(Long lessonId) {
//         return progressRepository.findAll().stream()
//                 .filter(p -> p.getMicroLesson().getId().equals(lessonId))
//                 .toList();
//     }
// }
