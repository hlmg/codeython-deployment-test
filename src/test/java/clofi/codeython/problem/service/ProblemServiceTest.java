package clofi.codeython.problem.service;

import clofi.codeython.problem.domain.Language;
import clofi.codeython.problem.domain.LanguageType;
import clofi.codeython.problem.domain.Problem;
import clofi.codeython.problem.domain.request.BaseCodeRequest;
import clofi.codeython.problem.domain.request.CreateProblemRequest;
import clofi.codeython.problem.repository.LanguageRepository;
import clofi.codeython.problem.repository.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProblemServiceTest {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @AfterEach
    void afterEach(){
        languageRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
    }

    @DisplayName("문제 등록")
    @Test
    void createProblemTest() {
        //given
        List<BaseCodeRequest> baseCodeRequests = new ArrayList<>();
        baseCodeRequests.add(new BaseCodeRequest(
                LanguageType.JAVA,
                """
                        public class Main{
                        public static void main(String[] args){
                        System.out.println("Hello World!");}
                        }
                    """));

        CreateProblemRequest createProblemRequest = getCreateProblemRequest(baseCodeRequests);

        //when
        Long problemId = problemService.createProblem(createProblemRequest);
        Problem problem = problemRepository.findById(problemId).get();
        //then
        assertThat(problem.getTitle()).isEqualTo("where is koreanCow");
        assertThat(problem.getContent()).isEqualTo("koreanCow is delicious");
        assertThat(problem.getLimitFactor()).isEqualTo("Never eat dog");
        assertThat(problem.getLimitTime()).isEqualTo(60);
        assertThat(problem.getDifficulty()).isEqualTo(1);

        for (BaseCodeRequest baseCodeRequest : baseCodeRequests) {
            Language language = createProblemRequest.toLanguage(
                    problem,baseCodeRequest.getLanguage(),baseCodeRequest.getCode());
            assertThat(language.getLanguage()).isEqualTo(LanguageType.JAVA);
            assertThat(language.getBaseCode()).isEqualTo(
            """
                        public class Main{
                        public static void main(String[] args){
                        System.out.println("Hello World!");}
                        }
                    """);
        }
    }

    @DisplayName("문제 제목 중복")
    @Test
    void createProblemTitleExceptionTest() {
        //given
        Problem problem = new Problem(
                "where is koreanCow",
                "koreanCow is delicious",
                "Never eat dog",
                60,
                1
        );
        problemRepository.save(problem);

        //when
        List<BaseCodeRequest> baseCodeRequests = new ArrayList<>();
        baseCodeRequests.add(new BaseCodeRequest(
                LanguageType.JAVA,
                """
                        public class Main{
                        public static void main(String[] args){
                        System.out.println("Hello World!");}
                        }
                    """));

        CreateProblemRequest createProblemRequest = getCreateProblemRequest(baseCodeRequests);

        //then
        assertThatThrownBy(() ->
                problemService.createProblem(createProblemRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 만들어진 문제 제목입니다.");

    }


    @DisplayName("언어 지원")
    @Test
    void createProblemWithLanguageTypeTest() {
        //given
        List<BaseCodeRequest> baseCodeRequests = new ArrayList<>();

        //then
        assertThatThrownBy(() ->
        baseCodeRequests.add(new BaseCodeRequest(
                LanguageType.valueOf("MATLAB") ,
                """
                        public class Main{
                        public static void main(String[] args){
                        System.out.println("Hello World!");}
                        }
                    """)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No enum constant clofi.codeython.problem.domain.LanguageType.MATLAB");

    }

    private static CreateProblemRequest getCreateProblemRequest(List<BaseCodeRequest> baseCodeRequests) {
        return new CreateProblemRequest(
                "where is koreanCow",
                "koreanCow is delicious",
                "Never eat dog",
                60,
                1,
                baseCodeRequests
        );
    }

}