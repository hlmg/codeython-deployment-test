package clofi.codeython.problem.repository;

import clofi.codeython.problem.domain.Language;
import clofi.codeython.problem.domain.LanguageType;
import clofi.codeython.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Language findByLanguageNo(Long LanguageNo);

    Language findByProblemNoAndLanguage(Problem ProblemNo, LanguageType Language);


}