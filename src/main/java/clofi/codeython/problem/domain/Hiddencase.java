package clofi.codeython.problem.domain;

import clofi.codeython.common.util.StringListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Getter
@NoArgsConstructor
public class Hiddencase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hiddencase_no", nullable = false)
    private Long hiddencaseNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_no", nullable = false)
    private Problem problem;

    @Convert(converter = StringListConverter.class)
    @Column(name = "input", nullable = false, columnDefinition = "TEXT")
    private List<String> input;

    @Column(name = "output", nullable = false, columnDefinition = "TEXT")
    private String output;

    public Hiddencase(Problem problem, List<String> input, String output) {
        this.problem = problem;
        this.input = input;
        this.output = output;
    }
}
