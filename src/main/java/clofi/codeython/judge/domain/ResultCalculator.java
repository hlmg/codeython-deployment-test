package clofi.codeython.judge.domain;

import clofi.codeython.judge.domain.runner.CodeRunner;
import clofi.codeython.problem.domain.LanguageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResultCalculator {
    private final Map<String, CodeRunner> codeRunnerMap;

    public int calculate(List<Hiddencase> hiddencases, String route, String language) {
        CodeRunner codeRunner = codeRunnerMap.get(LanguageType.getCodeRunnerName(language));
        int total = hiddencases.size();
        int success = 0;
        for (Hiddencase hiddencase : hiddencases) {
            String result = codeRunner.run(route, hiddencase.getInputs());
            if (isMatch(result, hiddencase.getOutput())) {
                success++;
            }
        }
        return success * 100 / total;
    }

    private boolean isMatch(String executionResult, String output) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            executionResult = mapper.writeValueAsString(mapper.readTree(executionResult));
            output = mapper.writeValueAsString(mapper.readTree(output));
        } catch (JsonProcessingException ignored) {
        }
        executionResult = executionResult.trim();
        log.info("executionResult={}{}", System.lineSeparator(), executionResult);
        log.info("output={}{}", System.lineSeparator(), output);

        return executionResult.equals(output);
    }

}
