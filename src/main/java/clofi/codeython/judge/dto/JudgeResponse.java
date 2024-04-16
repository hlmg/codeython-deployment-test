package clofi.codeython.judge.dto;

import lombok.Getter;

@Getter
public class JudgeResponse {
    private final int accuracy;

    public JudgeResponse(int accuracy) {
        this.accuracy = accuracy;
    }
}
