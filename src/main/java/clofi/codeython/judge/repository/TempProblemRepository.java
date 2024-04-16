package clofi.codeython.judge.repository;

import clofi.codeython.judge.domain.Problem;
import java.util.Optional;

public interface TempProblemRepository {
    Optional<Problem> findByProblemNo(Long problemNo);
}
