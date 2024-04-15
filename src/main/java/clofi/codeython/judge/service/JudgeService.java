package clofi.codeython.judge.service;

import clofi.codeython.judge.domain.Hiddencase;
import clofi.codeython.judge.domain.Problem;
import clofi.codeython.judge.domain.ResultCalculator;
import clofi.codeython.judge.domain.creator.RuntimeEnvironmentCreator;
import clofi.codeython.judge.dto.JudgeRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class JudgeService {
    private final RuntimeEnvironmentCreator runtimeEnvironmentCreator;
    private final ResultCalculator resultCalculator;

    public int judge(JudgeRequest judgeRequest) throws IOException {
        // TODO: 문제 번호로 문제 조회하기
        Problem problem = new Problem(List.of(
                new Hiddencase(List.of("5", "[1,2,3,4,5]"), "[2, 4, 6, 8, 10]"),
                new Hiddencase(List.of("4", "[1,2,3,4]"), "[2,4,6,8]"),
                new Hiddencase(List.of("5", "[1,2,3,4,5]"), "[2,4,6,8,10]")
        ), List.of("int", "int[]"), "int[]");

        String route = UUID.randomUUID() + "/";
        createFolder(route);
        // TODO: 언어에 맞는 구현체 사용
        runtimeEnvironmentCreator.config(problem.inputTypes, judgeRequest.getCode(), route);
        int result = resultCalculator.calculate(problem.hiddencases, route, problem.outputType);
        cleanup(route);
        return result;
    }

    private void createFolder(String route) {
        route = route.substring(0, route.length() - 1);
        try {
            Files.createDirectory(Path.of(route));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanup(String route) {
        try {
            FileUtils.deleteDirectory(new File(route));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
