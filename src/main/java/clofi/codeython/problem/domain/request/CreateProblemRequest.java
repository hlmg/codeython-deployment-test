package clofi.codeython.problem.domain.request;

import clofi.codeython.problem.domain.Language;
import clofi.codeython.problem.domain.LanguageType;
import clofi.codeython.problem.domain.Problem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProblemRequest {

    @NotBlank(message = "문제제목은 공백일 수 없습니다.")
    private String title;

    @NotBlank(message = "문제설명은 공백일 수 없습니다.")
    private String content;

    @NotBlank(message = "제한사항은 공백일 수 없습니다.")
    private String limitFactor;

    @NotBlank(message = "제한시간은 공백일 수 없습니다.")
    @Size(min = 10, message = "")
    private int limitTime;

    @NotBlank(message = "난이도는 공백일 수 없습니다.")
    @Size(min = 1, max = 5, message = "난이도는 최소 1에서 최대 5입니다.")
    private int difficulty;

    private List<BaseCodeRequest> baseCodes;

    public Problem toProblem() {
        return new Problem(
                title,
                content,
                limitFactor,
                limitTime,
                difficulty
        );
    }
    public Language toLanguage(Problem problemNo, LanguageType language, String code) {
        return new Language(
                problemNo,
                language,
                code
        );
    }
}
