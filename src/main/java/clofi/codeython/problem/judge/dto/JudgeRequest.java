package clofi.codeython.problem.judge.dto;

import lombok.Getter;

@Getter
public class JudgeRequest {
    private String language;
    private String code;

    public JudgeRequest(String language, String code) {
        this.language = language;
        this.code = code;
    }
}
