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

    public String evaluate(Code code) {
        ExecutionRequest executionRequest = new ExecutionRequest(
                "javascript", "18.15.0",
                List.of(code.getCode()));

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
}
