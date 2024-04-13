package clofi.codeython.problem.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_no", nullable = false)
    private Long problemNo;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "title", columnDefinition = "TEXT")
    private String constraint;

    @Column(name = "limit_time", nullable = false)
    private int limitTime;

    @Column(name = "difficulty", nullable = false, columnDefinition = "int default 1")
    private int difficulty;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
