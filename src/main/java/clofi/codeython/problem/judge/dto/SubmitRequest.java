package clofi.codeython.problem.judge.dto;

import lombok.Getter;

@Getter
public class SubmitRequest {
    private final String language;
    private final String code;
    private final Long roomId;

    public SubmitRequest(String language, String code, Long roomId) {
        this.language = language;
        this.code = code;
        this.roomId = roomId;
    }
}
