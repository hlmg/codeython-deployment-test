package clofi.codeython.problem.domain;

import jakarta.persistence.*;

@Entity
public class Testcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testcase_no", nullable = false)
    private Long testcaseNo;

    @ManyToOne
    @JoinColumn(name = "problem_no", nullable = false)
    private Problem problemNo;

    @Column(name = "input", nullable = false, columnDefinition = "TEXT")
    private String input;

    @Column(name = "output", nullable = false, columnDefinition = "TEXT")
    private String output;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
}
