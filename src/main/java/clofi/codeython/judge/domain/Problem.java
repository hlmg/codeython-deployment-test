package clofi.codeython.judge.domain;

import java.util.List;
import lombok.Getter;

// TODO: 채점 기능에서 활용할 임시 클래스
@Getter
public class Problem {
    public Long problemNo;
    public List<Hiddencase> hiddencases;
    public List<String> inputTypes;

    public Problem(Long problemNo, List<Hiddencase> hiddencases, List<String> inputTypes) {
        this.problemNo = problemNo;
        this.hiddencases = hiddencases;
        this.inputTypes = inputTypes;
    }
}
