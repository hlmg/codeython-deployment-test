package clofi.codeython.judge.domain.runner;

import java.io.IOException;
import java.util.List;

public interface TestRunner {
    boolean run(String code, List<String> input, String output) throws IOException;
}
