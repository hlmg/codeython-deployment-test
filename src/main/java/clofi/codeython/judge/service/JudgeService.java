package clofi.codeython.judge.service;

import clofi.codeython.judge.dto.JudgeRequest;
import clofi.codeython.judge.domain.Hiddencase;
import clofi.codeython.judge.domain.Problem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JudgeService {

    public int judge(JudgeRequest judgeRequest) throws IOException {
        // 요청: 문제 번호, 코드, 회원 정보, 언어
        // TODO: 문제 번호로 문제 조회하기
        Problem problem = new Problem(List.of(
                new Hiddencase(List.of("5", "[1,2,3,4,5]"), "[2,4,6,8,10]"),
                new Hiddencase(List.of("4", "[1,2,3,4]"), "[2,4,6,8]"),
                new Hiddencase(List.of("5", "[1,2,3,4,5]"), "[2,4,6,8,10]")
        ), List.of("int", "int[]"), "int[]");

        // 타입 정보로 실행 환경 구성
        String route = UUID.randomUUID() + "/";
        createExecutionEnvironment(problem.inputTypes, judgeRequest.getCode(), route);

        // 코드 실행
        int total = problem.hiddencases.size();
        int success = 0;
        for (Hiddencase hiddencase : problem.hiddencases) {
            String executionResult = execute(route, hiddencase.getInputs());
            boolean match = match(executionResult, hiddencase.getOutput());
            if (match) {
                success++;
            }
        }
        int result = success * 100 / total;

        cleanup(route);
        return result;
    }

    private void createExecutionEnvironment(List<String> inputTypes, String code, String route) {
        createFolder(route);
        // TODO: Main 코드 문제별로 고정이기 때문에 저장하고 재사용하게 변경
        createMainFile(route, inputTypes);
        createSolutionFile(route, code);
        compile(route);
    }

    private void createFolder(String route) {
        route = route.substring(0, route.length() - 1);
        try {
            Files.createDirectory(Path.of(route));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createMainFile(String route, List<String> inputTypes) {
        String code = getMainCode(inputTypes);
        createFile(route, code, "Main.java");
    }

    private String getMainCode(List<String> inputTypes) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                import com.fasterxml.jackson.core.JsonProcessingException;
                import com.fasterxml.jackson.databind.ObjectMapper;

                public class Main {
                    public static void main(String[] args) throws JsonProcessingException {
                        Solution s = new Solution();
                        ObjectMapper mapper = new ObjectMapper();
                """);

        sb.append("System.out.print(mapper.writeValueAsString(s.solution(");

        for (int i = 0; i < inputTypes.size(); i++) {
            sb.append(String.format("""
                            mapper.readValue(args[%d], %s.class)""",
                    i, inputTypes.get(i)));

            if (i != inputTypes.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(")));}}");
        return sb.toString();
    }

    private void createFile(String route, String content, String fileName) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(route + fileName))) {
            printWriter.print(content);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private void createSolutionFile(String route, String code) {
        createFile(route, code, "Solution.java");
    }

    private void compile(String route) {
        log.info("Compiling... route={}", route);

        ArrayList<String> commands = new ArrayList<>(
                List.of("javac", "-cp", String.format("./libs/*:./%s", route), String.format("%sMain.java", route)));
        ProcessBuilder pb = new ProcessBuilder(commands);
        File error = new File(route + "error.txt");

        pb.redirectError(error);
        try {
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Successfully Compiled");
    }

    // TODO: Exception catch 로 수정하기
    private String execute(String route, List<String> inputs) throws IOException {
        ArrayList<String> commands = new ArrayList<>(
                List.of("java", "-cp", String.format("./libs/*:./%s", route), "Main"));

        for (String input : inputs) {
            commands.add(String.format("%s", input));
        }

        ProcessBuilder pb = new ProcessBuilder(commands);
        File error = new File(route + "error.txt");
        pb.redirectError(error);

        Process proc = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    private boolean match(String executionResult, String output) {
        executionResult = executionResult.trim();
        log.info("executionResult={}{}", System.lineSeparator(), executionResult);
        log.info("output={}{}", System.lineSeparator(), output);
        return executionResult.equals(output);
    }

    private void cleanup(String route) {
        try {
            FileUtils.deleteDirectory(new File(route));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
