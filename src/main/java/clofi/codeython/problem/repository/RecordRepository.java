package clofi.codeython.problem.repository;

import clofi.codeython.problem.domain.Problem;
import clofi.codeython.problem.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    Optional<Record> findByProblemNo(Problem problemNo);
}
