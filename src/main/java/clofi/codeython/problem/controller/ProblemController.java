package clofi.codeython.problem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import clofi.codeython.member.service.dto.CustomMemberDetails;
import clofi.codeython.problem.controller.response.AllProblemResponse;
import clofi.codeython.problem.controller.response.RecordResponse;
import clofi.codeython.problem.domain.request.CreateProblemRequest;
import clofi.codeython.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProblemController {

	private final ProblemService problemService;

	@PostMapping("/api/problems")
	public ResponseEntity<Long> createProblem(
		@RequestBody CreateProblemRequest createProblemRequest) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(problemService.createProblem(createProblemRequest));
	}

	@GetMapping("/api/problems")
	public ResponseEntity<List<AllProblemResponse>> getAllProblem() {

		return ResponseEntity.ok(problemService.getAllProblem());
	}

	@GetMapping("/api/recent-records")
	public ResponseEntity<List<RecordResponse>> getRecord(
		@AuthenticationPrincipal CustomMemberDetails userDetails) {
		String username = userDetails.getUsername();

		return ResponseEntity.ok(problemService.getRecord(username));
	}

}
