package clofi.codeython.evaluate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class Controller {
    private final Evaluator evaluator;

    @GetMapping("/test")
    public String test(@RequestBody EvaluateRequest evaluateRequest) {
        return evaluator.evaluate(evaluateRequest);
    }

    @GetMapping("/test2")
    public String test2(@RequestBody EvaluateRequest evaluateRequest) {
        return evaluator.evaluateJava(evaluateRequest);
    }
}
