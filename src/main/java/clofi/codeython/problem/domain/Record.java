package clofi.codeython.problem.domain;

import clofi.codeython.user.domain.Member;
import jakarta.persistence.*;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_no", nullable = false)
    private Long recordNo;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private Member userNo;

    @ManyToOne
    @JoinColumn(name = "problem_no", nullable = false)
    private Problem problemNo;

    @Column(name = "language", nullable = false, length = 20)
    private String language;

    private int rate;
}
