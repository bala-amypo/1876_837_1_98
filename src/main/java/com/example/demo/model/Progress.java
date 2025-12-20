@Entity
@Table(name = "progress",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","micro_lesson_id"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="micro_lesson_id")
    private MicroLesson microLesson;

    private String status;
    private Integer progressPercent;
    private BigDecimal score;
    private LocalDateTime lastAccessedAt;

    @PrePersist
    void init() {
        lastAccessedAt = LocalDateTime.now();
    }
}
