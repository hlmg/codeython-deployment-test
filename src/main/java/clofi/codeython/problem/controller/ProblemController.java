package clofi.codeython.problem.controller;

import clofi.codeython.problem.controller.response.AllProblemResponse;
import clofi.codeython.problem.domain.request.CreateProblemRequest;
import clofi.codeython.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping("/api/problems")
    public ResponseEntity<Long> createProblem(
            @RequestBody CreateProblemRequest createProblemRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(problemService.createProblem(createProblemRequest));
    }

    @GetMapping("api/problems")
    public ResponseEntity<List<AllProblemResponse>> getAllProblem() {

        return ResponseEntity.ok(problemService.getAllProblem());
    }

}
