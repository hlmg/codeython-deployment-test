package clofi.codeython.problem.repository;

import clofi.codeython.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Optional<Problem> findByProblemNo(Long problemNo);

    Optional<Problem> findByTitle(String title);
}
