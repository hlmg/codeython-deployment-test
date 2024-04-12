package clofi.codeython.evaluate;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ExecutionResult {
    private String language;
    private String version;
    private RunResult run;

    @ToString
    @Getter
    public static class RunResult {
        private String stdout;
        private String stderr;
        private int code;
        private String signal;
        private String output;
    }
}
