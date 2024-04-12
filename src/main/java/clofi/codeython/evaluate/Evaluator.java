package clofi.codeython.evaluate;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class Evaluator {
    private final RestClient client = RestClient.builder().baseUrl("https://emkc.org").build();

    public String evaluate(EvaluateRequest evaluateRequest) {
        ExecutionRequest executionRequest = new ExecutionRequest(
                evaluateRequest.getLanguage().getLanguage(), evaluateRequest.getLanguage().getVersion(),
                List.of(evaluateRequest.getCode()));

        ResponseEntity<ExecutionResult> result = client.post()
                .uri("/api/v2/piston/execute")
                .body(executionRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    HttpStatusCode statusCode = response.getStatusCode();
                    HttpHeaders headers = response.getHeaders();
                    System.out.println("statusCode = " + statusCode);
                    System.out.println("headers = " + headers);

                })
                .toEntity(ExecutionResult.class);

        System.out.println("Response status: " + result.getStatusCode());
        System.out.println("Response headers: " + result.getHeaders());
        System.out.println("Contents: " + result.getBody());
        return result.getBody().getRun().getOutput();
    }

    public String evaluateJava(EvaluateRequest evaluateRequest) {
        //TODO: evaludateRequest의 문제 번호로 문제 조회하기
        Problem problem = new Problem(List.of("5", "new int[]{1,2,3,4,5}"), "2,4,6,8,10");

        String code = evaluateRequest.getCode();
        String javaCode = getJavaCode(code, problem.inputs);

        System.out.println(javaCode);

        ExecutionRequest executionRequest = new ExecutionRequest(
                evaluateRequest.getLanguage().getLanguage(), evaluateRequest.getLanguage().getVersion(),
                List.of(javaCode));

        ResponseEntity<ExecutionResult> result = client.post()
                .uri("/api/v2/piston/execute")
                .body(executionRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    HttpStatusCode statusCode = response.getStatusCode();
                    HttpHeaders headers = response.getHeaders();
                    System.out.println("statusCode = " + statusCode);
                    System.out.println("headers = " + headers);

                })
                .toEntity(ExecutionResult.class);

        System.out.println("Contents: " + result.getBody());
        return result.getBody().getRun().getOutput();
    }

    public String getJavaCode(String code, List<String> inputs) {
        String input = String.join(",", inputs);

        String importStatement = code.substring(0, code.indexOf("class Solution {"));
        code = code.substring(code.indexOf("class Solution {"));

        String basecode = """
                %s
                public class Main {
                    public static void main(String[] args) {
                        Solution solution = new Solution();
                        solution.solution(%s);
                    }
                }
                %s
                """;

        return String.format(basecode, importStatement, input, code);
    }
}
