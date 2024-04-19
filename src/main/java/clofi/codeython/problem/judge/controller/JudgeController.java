package clofi.codeython.problem.judge.controller;

import clofi.codeython.problem.judge.dto.SubmitRequest;
import clofi.codeython.problem.judge.dto.SubmitResponse;
import clofi.codeython.problem.judge.service.JudgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JudgeController {
    private final JudgeService judgeService;

    @PostMapping("/api/problems/{problemId}/result")
    public SubmitResponse submit(@RequestBody SubmitRequest submitRequest,
                                 @PathVariable("problemId") long problemNo) {
        return new SubmitResponse(judgeService.submit(submitRequest, problemNo), null, null);
    }

}
