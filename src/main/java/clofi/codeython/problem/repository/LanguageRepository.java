package clofi.codeython.problem.repository;

import clofi.codeython.problem.domain.Language;
import clofi.codeython.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Language findByLanguageNo(Long LanguageNo);

    List<Language> findByProblem(Problem problem);


}