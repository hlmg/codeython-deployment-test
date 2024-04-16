package clofi.codeython.judge.domain;

import clofi.codeython.judge.domain.runner.CodeRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResultCalculator {
    private final CodeRunner codeRunner;

    public int calculate(List<Hiddencase> hiddencases, String route, String outputType) {
        int total = hiddencases.size();
        int success = 0;
        for (Hiddencase hiddencase : hiddencases) {
            String result = codeRunner.run(route, hiddencase.getInputs());
            if (isMatch(result, hiddencase.getOutput(), outputType)) {
                success++;
            }
        }
        return success * 100 / total;
    }

    private boolean isMatch(String executionResult, String output, String outputType) {
        executionResult = executionResult.trim();
        ObjectMapper mapper = new ObjectMapper();
        Class<?> clazz = getClass(outputType);
        try {
            output = mapper.writeValueAsString(mapper.readValue(output, clazz));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
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
