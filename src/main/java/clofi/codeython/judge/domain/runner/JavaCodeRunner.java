package clofi.codeython.judge.domain.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JavaCodeRunner implements CodeRunner {

    @Override
    public String run(String route, List<String> inputs) {
        ArrayList<String> command = new ArrayList<>(
                List.of("java", "-cp", String.format("./libs/*:./%s", route), "Main"));

        for (String input : inputs) {
            command.add(String.format("%s", input));
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command);

        StringBuilder builder = new StringBuilder();
        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("프로세스 실행에 실패했습니다.", e);
        }

        return builder.toString();
    }
}
