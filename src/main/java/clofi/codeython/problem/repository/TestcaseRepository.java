package clofi.codeython.problem.repository;

import clofi.codeython.problem.domain.Problem;
import clofi.codeython.problem.domain.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {

    Testcase findByProblem(Problem problem);

}
