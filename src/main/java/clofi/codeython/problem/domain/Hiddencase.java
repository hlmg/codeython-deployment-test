package clofi.codeython.problem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
public class Hiddencase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hiddencase_no", nullable = false)
    private Long hiddencaseNo;

    @ManyToOne
    @JoinColumn(name = "problem_no", nullable = false)
    private Problem problemNo;

    @Column(name = "input", nullable = false, columnDefinition = "TEXT")
    private String input;

    @Column(name = "output", nullable = false, columnDefinition = "TEXT")
    private String output;
}
