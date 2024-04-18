package clofi.codeython.problem.repository;

import clofi.codeython.problem.domain.Language;
import clofi.codeython.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Language findByLanguageNo(Long LanguageNo);

    Language findByProblem(Problem problem);


}