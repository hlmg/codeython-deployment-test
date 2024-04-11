package clofi.codeython.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import clofi.codeython.user.domain.request.CreateMemberRequest;
import clofi.codeython.user.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/api/signup")
	public ResponseEntity<Long> signUp(@Valid @RequestBody CreateMemberRequest createMemberRequest) {

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(memberService.signUp(createMemberRequest));
	}
}
