package clofi.codeython.problem.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import clofi.codeython.member.domain.Member;
import clofi.codeython.member.repository.MemberRepository;
import clofi.codeython.problem.controller.response.AllProblemResponse;
import clofi.codeython.problem.controller.response.RecordResponse;
import clofi.codeython.problem.domain.Language;
import clofi.codeython.problem.domain.LanguageType;
import clofi.codeython.problem.domain.Problem;
import clofi.codeython.problem.domain.Record;
import clofi.codeython.problem.domain.request.BaseCodeRequest;
import clofi.codeython.problem.domain.request.CreateProblemRequest;
import clofi.codeython.problem.repository.LanguageRepository;
import clofi.codeython.problem.repository.ProblemRepository;
import clofi.codeython.problem.repository.RecordRepository;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class ProblemServiceTest {
	@Autowired
	private ProblemService problemService;
	@Autowired
	private ProblemRepository problemRepository;
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private RecordRepository recordRepository;

	@AfterEach
	void afterEach() {
		languageRepository.deleteAllInBatch();
		recordRepository.deleteAllInBatch();
		problemRepository.deleteAllInBatch();
		memberRepository.deleteAllInBatch();
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
				problem, baseCodeRequest.getLanguage(), baseCodeRequest.getCode());
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
				LanguageType.valueOf("MATLAB"),
				"""
					    public class Main{
					    public static void main(String[] args){
					    System.out.println("Hello World!");}
					    }
					""")))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("No enum constant clofi.codeython.problem.domain.LanguageType.MATLAB");

	}

	@DisplayName("문제 목록 조회")
	@Test
	void getAllProblemTest() {
		//given
		List<BaseCodeRequest> baseCodeRequests1 = new ArrayList<>();
		baseCodeRequests1.add(new BaseCodeRequest(
			LanguageType.JAVA,
			"""
				    public class Main{
				    public static void main(String[] args){
				    System.out.println("Hello World!");}
				    }
				"""));
		CreateProblemRequest createProblemRequest = getCreateProblemRequest(baseCodeRequests1);
		Long problemId = problemService.createProblem(createProblemRequest);

		//when
		List<AllProblemResponse> allProblem = problemService.getAllProblem();

		//then
		AllProblemResponse problem = allProblem.get(0);
		Assertions.assertThat(1).isEqualTo(allProblem.size());
		Assertions.assertThat("where is koreanCow").isEqualTo(problem.title());

		//given
		List<BaseCodeRequest> baseCodeRequests2 = new ArrayList<>();
		baseCodeRequests2.add(new BaseCodeRequest(
			LanguageType.JAVA,
			"""
				    public class Main{
				    public static void main(String[] args){
				    System.out.println("Hello World!");}
				    }
				"""));
		CreateProblemRequest createProblemRequest2 = getCreateProblemRequest2(baseCodeRequests2);
		Long problemId2 = problemService.createProblem(createProblemRequest2);

		//when
		List<AllProblemResponse> allProblem2 = problemService.getAllProblem();

		//then
		Assertions.assertThat(2).isEqualTo(allProblem2.size());

	}

	@DisplayName("문제조회 - 문제가 없을 경우")
	@Test
	void getAllProblemWithNotTest() {
		//given
		//when
		//then
		assertThatThrownBy(() ->
			problemService.getAllProblem())
			.isInstanceOf(EntityNotFoundException.class)
			.hasMessage("등록된 문제가 없습니다.");
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

	private static CreateProblemRequest getCreateProblemRequest2(List<BaseCodeRequest> baseCodeRequests) {
		return new CreateProblemRequest(
			"where is koreanCow2",
			"koreanCow is delicious2",
			"Never eat dog2",
			60,
			1,
			baseCodeRequests
		);
	}

	@DisplayName("최근에 푼 문제의 기록을 갖고 올 수 있다.")
	@Test
	void getRecordTest() {
		//given
		Member member = new Member("test", "test123", "steet");
		memberRepository.save(member);

		Problem problem = new Problem("제목", "내용", "제한사항", 10, 10);
		problemRepository.save(problem);

		Record record = new Record("테스트코드", member, problem, "test언어", 40, null, null);
		recordRepository.save(record);

		//when
		List<RecordResponse> records = problemService.getRecord(member.getUsername());
		//then
		assertThat(records.getFirst().recordId()).isEqualTo(record.getRecordNo());
		assertThat(records.getFirst().title()).isEqualTo("제목");
		assertThat(records.getFirst().accuracy()).isEqualTo(40);
		assertThat(records.getFirst().grade()).isEqualTo(null);

	}

}