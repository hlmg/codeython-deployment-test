package clofi.codeython.judge.domain.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

//@Component
public class JavascriptCodeRunner implements CodeRunner {

    @Override
    public String run(String route, List<String> inputs) {
        ArrayList<String> command = new ArrayList<>(
                List.of("node", route + "solution.js"));

        command.addAll(inputs);

        ProcessBuilder processBuilder = new ProcessBuilder(command);

        StringBuilder outputMessage = new StringBuilder();
        StringBuilder errorMessage = new StringBuilder();
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
                errorMessage.append(line);
                errorMessage.append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        if (!errorMessage.isEmpty()) {
            throw new IllegalArgumentException(errorMessage.toString());
        }

        return outputMessage.toString();
    }

}
