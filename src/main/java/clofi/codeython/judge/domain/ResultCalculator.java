package clofi.codeython.judge.domain;

import clofi.codeython.judge.domain.runner.TestRunner;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResultCalculator {
    private final TestRunner testRunner;

    public int calculate(List<Hiddencase> hiddencases, String route, String outputType) throws IOException {
        int total = hiddencases.size();
        int success = 0;
        for (Hiddencase hiddencase : hiddencases) {
            boolean result = testRunner.run(route, hiddencase.getInputs(), hiddencase.getOutput(), outputType);
            if (result) {
                success++;
            }
        }
        return success * 100 / total;
    }
}
