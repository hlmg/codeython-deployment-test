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

        StringBuilder outputMessage = new StringBuilder();
        try {
            Process process = processBuilder.start();
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = outputReader.readLine()) != null) {
                outputMessage.append(line);
                outputMessage.append(System.lineSeparator());
            }
            while ((line = errorReader.readLine()) != null) {
                outputMessage.append(line);
                outputMessage.append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return outputMessage.toString();
    }
}
