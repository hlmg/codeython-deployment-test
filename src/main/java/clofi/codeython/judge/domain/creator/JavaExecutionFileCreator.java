package clofi.codeython.judge.domain.creator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JavaExecutionFileCreator implements ExecutionFileCreator {

    @Override
    public void create(List<String> inputTypes, String code, String route) {
        // TODO: Main 코드 문제별로 고정이기 때문에 repository에 저장하고 재사용하게 변경
        createMainFile(route, inputTypes);
        createSolutionFile(route, code);
        compile(route);
    }

    private void createMainFile(String route, List<String> inputTypes) {
        String code = getMainCode(inputTypes);
        createFile(route, code, "Main.java");
    }

    private String getMainCode(List<String> inputTypes) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                import com.fasterxml.jackson.databind.ObjectMapper;

                public class Main {
                    public static void main(String[] args) throws Exception {
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
            throw new IllegalStateException(e);
        }
    }

    private void createSolutionFile(String route, String code) {
        createFile(route, code, "Solution.java");
    }

    private void compile(String route) {
        ArrayList<String> commands = new ArrayList<>(
                List.of("javac", "-cp", String.format("./libs/*:./%s", route), String.format("%sMain.java", route)));
        ProcessBuilder processBuilder = new ProcessBuilder(commands);

        StringBuilder errorMessage = new StringBuilder();
        try {
            Process process = processBuilder.start();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            process.waitFor();
            String line;
            while ((line = errorReader.readLine()) != null) {
                errorMessage.append(line);
                errorMessage.append(System.lineSeparator());
            }
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
        if (!errorMessage.isEmpty()) {
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }
}
