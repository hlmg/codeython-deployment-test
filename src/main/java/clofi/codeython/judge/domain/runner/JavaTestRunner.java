package clofi.codeython.judge.domain.runner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public boolean run(String route, List<String> inputs, String output, String outputType) throws IOException {
        String executionResult = execute(route, inputs);
        return match(executionResult, output, outputType);
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

    // TODO: Matcher 클래스로 분리하기
    private boolean match(String executionResult, String output, String outputType) {
        executionResult = executionResult.trim();
        ObjectMapper mapper = new ObjectMapper();
        Class<?> clazz = getClass(outputType);
        try {
            output = mapper.writeValueAsString(mapper.readValue(output, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("executionResult={}{}", System.lineSeparator(), executionResult);
        log.info("output={}{}", System.lineSeparator(), output);

        return executionResult.equals(output);
    }

    private Class<?> getClass(String type) {
        return switch (type) {
            case "int" -> int.class;
            case "int[]" -> int[].class;
            case "double" -> double.class;
            case "double[]" -> double[].class;
            case "String" -> String.class;
            case "String[]" -> String[].class;
            case "boolean" -> boolean.class;
            case "boolean[]" -> boolean[].class;
            default -> throw new IllegalArgumentException("Invalid output type");
        };
    }
}
