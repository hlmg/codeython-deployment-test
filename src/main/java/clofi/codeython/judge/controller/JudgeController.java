package clofi.codeython.judge.controller;

import clofi.codeython.judge.dto.JudgeRequest;
import clofi.codeython.judge.service.JudgeService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JudgeController {
    private final JudgeService judgeService;

    @PostMapping("/submit")
    public int submit(@RequestBody JudgeRequest judgeRequest) throws IOException {
        return judgeService.judge(judgeRequest);
    }
}
