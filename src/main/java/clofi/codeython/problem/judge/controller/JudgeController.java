package clofi.codeython.problem.judge.controller;

import clofi.codeython.problem.judge.dto.JudgeRequest;
import clofi.codeython.problem.judge.dto.JudgeResponse;
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
    public JudgeResponse submit(@RequestBody JudgeRequest judgeRequest,
                                @PathVariable("problemId") Long problemNo) {
        return new JudgeResponse(judgeService.judge(judgeRequest, problemNo));
    }
}
