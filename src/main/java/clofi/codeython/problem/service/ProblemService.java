package clofi.codeython.problem.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import clofi.codeython.problem.controller.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clofi.codeython.member.domain.Member;
import clofi.codeython.member.repository.MemberRepository;
import clofi.codeython.problem.domain.Problem;
import clofi.codeython.problem.domain.Record;
import clofi.codeython.problem.domain.request.BaseCodeRequest;
import clofi.codeython.problem.domain.request.CreateProblemRequest;
import clofi.codeython.problem.domain.request.TestcaseRequest;
import clofi.codeython.problem.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final LanguageRepository languageRepository;
    private final TestcaseRepository testcaseRepository;
	private final RecordRepository recordRepository;
	private final MemberRepository memberRepository;

    public Long createProblem(CreateProblemRequest createProblemRequest){
        if (problemRepository.existsByTitle(createProblemRequest.getTitle())){
            throw new IllegalArgumentException("이미 만들어진 문제 제목입니다.");
        }

        Problem problem = problemRepository.save(createProblemRequest.toProblem());
        for (BaseCodeRequest baseCode : createProblemRequest.getBaseCodes()) {
            languageRepository.save(createProblemRequest.toLanguage(problem,baseCode.getLanguage(),baseCode.getCode()));
        }
        for (TestcaseRequest testcase : createProblemRequest.getTestcase()) {
            testcaseRepository.save(createProblemRequest.toTestcase(problem, testcase.getInputCase(), testcase.getOutputCase(), testcase.getDescription()));
        }

        return problem.getProblemNo();
    }

    public List<AllProblemResponse> getAllProblem() {
        List<Problem> problems = problemRepository.findAll();

        if (problems.isEmpty()){
            throw new EntityNotFoundException("등록된 문제가 없습니다.");
        }

        return problems.stream().map(problem -> {
            Optional<Record> record = recordRepository.findByProblem(problem);
            return record.map(value -> AllProblemResponse
                            .of(problem, value.getAccuracy(), true))
                    .orElseGet(() -> AllProblemResponse.of(problem, 0, false));
        }).collect(Collectors.toList());

    }

    public GetProblemResponse getProblem(Long problemNo) {

        if (problemRepository.findByProblemNo(problemNo) == null){
            throw new EntityNotFoundException("등록된 문제가 없습니다.");
        }
        Problem problem = problemRepository.findByProblemNo(problemNo);

        List<BaseCodeResponse> baseCodes = languageRepository.findByProblem(problem)
                .stream()
                .map(bc -> new BaseCodeResponse(bc.getLanguage(),bc.getBaseCode()))
                .collect(Collectors.toList());

        List<TestcaseResponse> testcases = testcaseRepository.findByProblem(problem)
                .stream()
                .map(tc -> new TestcaseResponse(tc.getInput(),tc.getOutput(),tc.getDescription()))
                .collect(Collectors.toList());

        return GetProblemResponse.of(
                problem,
                baseCodes,
                testcases);
    }

    public List<RecordResponse> getRecord(String userName) {
        Member member = memberRepository.findByUsername(userName);
        return recordRepository.findAllByMemberOrderByUpdatedAtDesc(member).stream()
                .map(record -> {
                    Problem problem = problemRepository.findByProblemNo(record.getProblem().getProblemNo());
                    return RecordResponse.of(record, problem.getTitle());
                }).collect(Collectors.toList());
    }
}
