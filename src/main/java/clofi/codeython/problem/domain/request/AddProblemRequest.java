package clofi.codeython.problem.domain.request;

import clofi.codeython.problem.domain.Problem;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProblemRequest {

    @NotBlank(message = "문제제목은 공백일 수 없습니다.")
    private String title;

    @NotBlank(message = "문제설명은 공백일 수 없습니다.")
    private String content;

    private String constraint;

    @NotBlank(message = "제한시간은 공백일 수 없습니다.")
    private int limitTime;

    @NotBlank(message = "난이도는 공백일 수 없습니다.")
    private int difficulty;

    public Problem toProblem() {
        return new Problem(
                title,
                content,
                constraint,
                limitTime,
                difficulty
        );
    }
}
