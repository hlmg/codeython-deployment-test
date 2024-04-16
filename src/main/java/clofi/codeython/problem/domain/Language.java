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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_no", nullable = false)
    private Problem problemNo;

    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private LanguageType language;

    @Column(name = "base_code", nullable = false, columnDefinition = "TEXT")
    private String baseCode;

    public Language(Problem problemNo, LanguageType language, String baseCode) {
        this.problemNo = problemNo;
        this.language = language;
        this.baseCode = baseCode;
    }
}
