package clofi.codeython.evaluate;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class Controller {
    private final Evaluator evaluator;

    @PostMapping("/submit")
    public int submit(@RequestBody EvaluateRequest evaluateRequest) throws IOException {
        return evaluator.executeJudge(evaluateRequest);
    }
}
