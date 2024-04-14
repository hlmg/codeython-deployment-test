package clofi.codeython.problem.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity @Getter
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_no", nullable = false)
    private Long problemNo;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "constraint", columnDefinition = "TEXT")
    private String constraint;

    @Column(name = "limit_time", nullable = false)
    private int limitTime;

    @Column(name = "difficulty", nullable = false, columnDefinition = "int default 1")
    private int difficulty;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Problem(String title, String content, String constraint,
                   int limitTime, int difficulty) {
        this.title = title;
        this.content = content;
        this.constraint = constraint;
        this.limitTime = limitTime;
        this.difficulty = difficulty;
    }

}
