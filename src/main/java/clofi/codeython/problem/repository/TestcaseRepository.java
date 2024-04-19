package clofi.codeython.problem.repository;

import clofi.codeython.problem.domain.Problem;
import clofi.codeython.problem.domain.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {

    List<Testcase> findByProblem(Problem problem);

    List<Testcase> findAllByProblemProblemNo(Long problemNo);
}
