package clofi.codeython.judge.repository;

import clofi.codeython.judge.domain.Hiddencase;
import clofi.codeython.judge.domain.Problem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TempTempProblemRepositoryImpl implements TempProblemRepository {
    private static final Map<Long, Problem> problems = new HashMap<>() {{
        put(1L, new Problem(1L, List.of(
                new Hiddencase(List.of("2", "[1,2]"), "[2, 4]"),
                new Hiddencase(List.of("3", "[1,2,3]"), "[2,4,6]")
        ), List.of("int", "int[]")));
        put(2L, new Problem(2L, List.of(
                new Hiddencase(List.of("[1,2]", "[A, B]"), "[1A, 2B]")
        ), List.of("int[]", "String[]")));
    }};

    @Override
    public Optional<Problem> findByProblemNo(Long problemNo) {
        return Optional.ofNullable(problems.get(problemNo));
    }
}
