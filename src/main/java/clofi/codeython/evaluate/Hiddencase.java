package clofi.codeython.evaluate;

import java.util.List;
import lombok.Getter;

// TODO: 채점 기능에서 활용할 임시 클래스
@Getter
public class Hiddencase {
    private final List<String> inputs;
    private final String output;

    public Hiddencase(List<String> inputs, String output) {
        this.inputs = inputs;
        this.output = output;
    }
}
