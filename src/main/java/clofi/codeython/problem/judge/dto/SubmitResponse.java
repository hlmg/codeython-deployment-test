package clofi.codeython.problem.judge.dto;

import lombok.Getter;

@Getter
public class SubmitResponse {
    private final int accuracy;
    private final Integer grade;
    private final Integer gainExp;

    public SubmitResponse(int accuracy, Integer grade, Integer gainExp) {
        this.accuracy = accuracy;
        this.grade = grade;
        this.gainExp = gainExp;
    }
}
