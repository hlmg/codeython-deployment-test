package clofi.codeython.evaluate;

import lombok.Getter;

@Getter
public class EvaluateRequest {
    private Language language;
    private String code;
}
