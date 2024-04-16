package clofi.codeython.judge.domain.runner;

import java.util.List;

public interface CodeRunner {
    String run(String code, List<String> input);
}
