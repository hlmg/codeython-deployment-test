package clofi.codeython.problem.service;

import clofi.codeython.problem.domain.Problem;
import clofi.codeython.problem.domain.request.BaseCodeRequest;
import clofi.codeython.problem.domain.request.CreateProblemRequest;
import clofi.codeython.problem.repository.LanguageRepository;
import clofi.codeython.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final LanguageRepository languageRepository;

    public Long createProblem(CreateProblemRequest createProblemRequest){
        if (problemRepository.findByTitle(createProblemRequest.getTitle()).isPresent()){
            throw new IllegalArgumentException("이미 만들어진 문제 제목입니다.");
        }

        Problem problem = problemRepository.save(createProblemRequest.toProblem());
        for (BaseCodeRequest baseCode : createProblemRequest.getBaseCodes()) {
            languageRepository.save(createProblemRequest.toLanguage(problem,baseCode.getLanguage(),baseCode.getCode()));
        }

        return problem.getProblemNo();
    }

}
