package clofi.codeython.judge.domain.creator;

import java.util.List;

public interface RuntimeEnvironmentCreator {
    void config(List<String> inputTypes, String code, String route);
}
