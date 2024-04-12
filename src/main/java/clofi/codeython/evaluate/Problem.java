package clofi.codeython.evaluate;

import java.util.List;
import lombok.Getter;

// TODO: 채점 기능에서 활용할 임시 클래스
@Getter
public class Problem {
    public List<String> inputs;
    public String output;

    public Problem(List<String> inputs, String output) {
        this.inputs = inputs;
        this.output = output;
    }
}
