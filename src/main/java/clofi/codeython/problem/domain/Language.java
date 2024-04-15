package clofi.codeython.problem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long languageNo;

    @ManyToOne
    @JoinColumn(name = "problem_no", nullable = false)
    private Problem problemNo;

    @Column(name = "language", nullable = false, length = 20)
    private String language;

    @Column(name = "base_code", nullable = false, columnDefinition = "TEXT")
    private String baseCode;

}
