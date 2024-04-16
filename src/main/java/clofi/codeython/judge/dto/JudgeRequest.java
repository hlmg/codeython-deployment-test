package clofi.codeython.judge.dto;

import clofi.codeython.judge.domain.Language;
import lombok.Getter;

@Getter
public class JudgeRequest {
    private Language language;
    private String code;
    private Long problemNo;
}
