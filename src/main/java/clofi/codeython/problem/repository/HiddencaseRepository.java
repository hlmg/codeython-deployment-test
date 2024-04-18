package clofi.codeython.problem.repository;

import clofi.codeython.problem.domain.Hiddencase;
import clofi.codeython.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiddencaseRepository extends JpaRepository<Hiddencase, Long> {

    Hiddencase findByProblem(Problem problem);
}
