@Entity
@Table(name = "recommendations")
@Data
@Builder
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime generatedAt;
    private String recommendedLessonIds;
    private String basisSnapshot;
    private BigDecimal confidenceScore;

    @PrePersist
    public void prePersist() {
        generatedAt = LocalDateTime.now();
    }
}
