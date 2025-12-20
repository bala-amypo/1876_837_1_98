@Entity
@Table(name="recommendations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private LocalDateTime generatedAt;

    @Column(length = 500)
    private String recommendedLessonIds;

    @Column(length = 1000)
    private String basisSnapshot;

    private BigDecimal confidenceScore;

    @PrePersist
    void generated() {
        generatedAt = LocalDateTime.now();
    }
}
