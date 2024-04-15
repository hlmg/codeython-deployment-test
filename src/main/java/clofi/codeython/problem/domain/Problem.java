package clofi.codeython.problem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity @Getter
@DynamicInsert
@NoArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_no", nullable = false)
    private Long problemNo;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "limit_factor", columnDefinition = "TEXT")
    private String limitFactor;

    @Column(name = "limit_time", nullable = false)
    private int limitTime;

    @Column(name = "difficulty", nullable = false, columnDefinition = "int default 1")
    private int difficulty;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Problem(String title, String content, String limitFactor,
                   int limitTime, int difficulty) {
        this.title = title;
        this.content = content;
        this.limitFactor = limitFactor;
        this.limitTime = limitTime;
        this.difficulty = difficulty;
    }

}
