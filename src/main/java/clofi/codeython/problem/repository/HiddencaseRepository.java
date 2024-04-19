package clofi.codeython.problem.repository;

import clofi.codeython.problem.domain.Hiddencase;
import clofi.codeython.problem.domain.Problem;
import clofi.codeython.problem.domain.Testcase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiddencaseRepository extends JpaRepository<Hiddencase, Long> {

    List<Hiddencase> findAllByProblemProblemNo(Long problemNo);

    Hiddencase findByProblem(Problem problem);
}
