package clofi.codeython.evaluate;

import lombok.Getter;

@Getter
public class ExecutionResult {
    private String language;
    private String version;
    private RunResult run;

    @Getter
    public static class RunResult {
        private String stdout;
        private String stderr;
        private int code;
        private String signal;
        private String output;
    }
}
