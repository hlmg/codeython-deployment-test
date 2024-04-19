package clofi.codeython.problem.judge.domain;

import clofi.codeython.problem.judge.domain.runner.CodeRunner;
import clofi.codeython.problem.domain.Hiddencase;
import clofi.codeython.problem.domain.LanguageType;
import clofi.codeython.problem.domain.Testcase;
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

    public int calculate(String route, String language, List<Testcase> testcases, List<Hiddencase> hiddencases) {
        CodeRunner codeRunner = codeRunnerMap.get(LanguageType.getCodeRunnerName(language));
        int total = hiddencases.size() + testcases.size();
        int success = 0;

        for (Testcase testcase : testcases) {
            String result = codeRunner.run(route, testcase.getInput());
            if (isMatch(result, testcase.getOutput())) {
                success++;
            }
        }

        for (Hiddencase hiddencase : hiddencases) {
            String result = codeRunner.run(route, hiddencase.getInput());
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
