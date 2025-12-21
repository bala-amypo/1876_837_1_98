@Entity
@Table(name = "progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // NOT_STARTED, IN_PROGRESS, COMPLETED
    private Integer progressPercent;
    private LocalDateTime lastAccessedAt;
    private BigDecimal score;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"progresses","recommendations"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "micro_lesson_id", nullable = false)
    @JsonIgnoreProperties({"progressList"})
    private MicroLesson microLesson;

    @PrePersist
    public void prePersist() {
        this.lastAccessedAt = LocalDateTime.now();
    }
}
