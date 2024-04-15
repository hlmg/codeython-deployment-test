package clofi.codeython.problem.controller;

import clofi.codeython.problem.domain.request.AddProblemRequest;
import clofi.codeython.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping("/api/problems")
    public ResponseEntity<Long> addProblem(@RequestBody AddProblemRequest addProblemRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(problemService.addProblem(addProblemRequest));
    }

}
