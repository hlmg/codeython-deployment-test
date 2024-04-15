package clofi.codeython.judge.domain.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
// TODO: 예외 처리 로직 추가하기
public class JavaTestRunner implements TestRunner {

    @Override
    public boolean run(String route, List<String> inputs, String output) throws IOException {
        String executionResult = execute(route, inputs);
        return match(executionResult, output);
    }

    private String execute(String route, List<String> inputs) throws IOException {
        ArrayList<String> commands = new ArrayList<>(
                List.of("java", "-cp", String.format("./libs/*:./%s", route), "Main"));

        for (String input : inputs) {
            commands.add(String.format("%s", input));
        }

        ProcessBuilder pb = new ProcessBuilder(commands);
        File error = new File(route + "error.txt");
        pb.redirectError(error);

        Process proc = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    private boolean match(String executionResult, String output) {
        executionResult = executionResult.trim();
        log.info("executionResult={}{}", System.lineSeparator(), executionResult);
        log.info("output={}{}", System.lineSeparator(), output);
        return executionResult.equals(output);
    }
}
