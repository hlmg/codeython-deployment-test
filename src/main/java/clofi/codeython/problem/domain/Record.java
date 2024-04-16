package clofi.codeython.problem.domain;

import clofi.codeython.common.domain.BaseEntity;
import clofi.codeython.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_no", nullable = false)
    private Long recordNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private Member userNo;

    @ManyToOne
    @JoinColumn(name = "problem_no", nullable = false)
    private Problem problemNo;

    @Column(name = "language", nullable = false, length = 20)
    private String language;

    @Column(name = "accuracy", nullable = false, columnDefinition = "int default 0")
    private int accuracy;

    @Column(name = "grade")
    private int grade;

    @Column(name = "member_cnt")
    private int memberCnt;

}
