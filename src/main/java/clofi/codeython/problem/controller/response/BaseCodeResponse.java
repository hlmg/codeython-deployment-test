package clofi.codeython.problem.controller.response;

import clofi.codeython.problem.domain.LanguageType;
import lombok.Getter;

public record BaseCodeResponse(
        LanguageType language, String code) {
}
