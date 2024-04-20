package clofi.codeython.problem.repository;

import clofi.codeython.problem.domain.Problem;
import clofi.codeython.problem.domain.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {

    @Query("SELECT t FROM Testcase t JOIN FETCH t.input i WHERE t.problem = (:problem)")
    List<Testcase> findByProblem(@Param("problem") Problem problem);

    @Query("SELECT t FROM Testcase t JOIN FETCH t.input i WHERE t.problem.problemNo = (:problemNo)")
    List<Testcase> findAllByProblemProblemNo(@Param("problemNo") Long problemNo);
}
