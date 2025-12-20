package com.example.demo.repository;

import com.example.demo.model.MicroLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MicroLessonRepository extends JpaRepository<MicroLesson, Long> {
}
