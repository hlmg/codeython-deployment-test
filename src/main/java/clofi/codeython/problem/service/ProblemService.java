package clofi.codeython.problem.service;

import clofi.codeython.problem.domain.Problem;
import clofi.codeython.problem.domain.request.AddProblemRequest;
import clofi.codeython.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemService {
    private final ProblemRepository problemRepository;

    public Long addProblem(AddProblemRequest addProblemRequest){

        if (problemRepository.findByTitle(addProblemRequest.getTitle()).isPresent()){
            throw new IllegalArgumentException("이미 만들어진 문제 제목입니다.");
        }

        Problem problem = problemRepository.save(addProblemRequest.toProblem());
        return problem.getProblemNo();
    }

}
