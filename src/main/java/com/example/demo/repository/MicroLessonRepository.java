package com.example.demo.repository;
import com.example.demo.model.MicroLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MicroLessonRepository extends JpaRepository<MicroLesson, Long> {
    @Query("SELECT m FROM MicroLesson m WHERE (:tags IS NULL OR m.tags LIKE %:tags%) " +
           "AND (:diff IS NULL OR m.difficulty = :diff) " +
           "AND (:ct IS NULL OR m.contentType = :ct)")
    List<MicroLesson> findByFilters(@Param("tags") String tags, 
                                   @Param("diff") String diff, 
                                   @Param("ct") String ct);
}