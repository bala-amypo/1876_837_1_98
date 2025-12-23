// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.*;
// import java.time.LocalDateTime;
// import java.math.BigDecimal;

// @Entity
// @Table(name = "progress")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class Progress {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String status;
//     private int progressPercent;
//     private BigDecimal score;

//     @Column(name = "last_accessed_at")
//     private LocalDateTime lastAccessedAt;

//     private BigDecimal completion;

//     @ManyToOne
//     @JoinColumn(name = "user_id")
//     private User user;

//     @ManyToOne
//     @JoinColumn(name = "micro_lesson_id")
//     private MicroLesson microLesson;
// }
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "micro_lesson_id", nullable = false)
    private MicroLesson microLesson;

    @NotBlank
    @Builder.Default
    private String status = "NOT_STARTED";

    @NotNull
    @Min(0)
    @Max(100)
    private Integer progressPercent;

    private LocalDateTime lastAccessedAt;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        this.lastAccessedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastAccessedAt = LocalDateTime.now();
    }
}