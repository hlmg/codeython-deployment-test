package clofi.codeython.judge.service;

import clofi.codeython.judge.domain.Problem;
import clofi.codeython.judge.domain.ResultCalculator;
import clofi.codeython.judge.domain.creator.ExecutionFileCreator;
import clofi.codeython.judge.dto.JudgeRequest;
import clofi.codeython.judge.repository.TempProblemRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class JudgeService {
    private final ExecutionFileCreator executionFileCreator;
    private final ResultCalculator resultCalculator;
    // TODO: 실제 문제 저장소로 변경하기
    private final TempProblemRepository tempProblemRepository;

    public int judge(JudgeRequest judgeRequest, Long problemNo) {
        Problem problem = tempProblemRepository.findByProblemNo(problemNo)
                .orElseThrow(() -> new IllegalArgumentException("없는 문제 번호입니다."));

        String route = UUID.randomUUID() + "/";
        createFolder(route);
        // TODO: 언어에 맞는 구현체 사용
        try {
            executionFileCreator.create(problem.inputTypes, judgeRequest.getCode(), route);
            return resultCalculator.calculate(problem.hiddencases, route, problem.outputType);
        } finally {
            cleanup(route);
        }
    }

    private void createFolder(String route) {
        try {
            Files.createDirectory(Path.of(route));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void cleanup(String route) {
        try {
            FileUtils.deleteDirectory(new File(route));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
