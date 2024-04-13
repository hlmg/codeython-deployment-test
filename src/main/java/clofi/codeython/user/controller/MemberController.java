package clofi.codeython.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import clofi.codeython.user.controller.response.MemberResponse;
import clofi.codeython.user.domain.request.CreateMemberRequest;
import clofi.codeython.user.domain.request.UpdateMemberRequest;
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

	//TODO: pathVar 나중에 토큰으로 교체
	@GetMapping("/api/users/{userNo}")
	public ResponseEntity<MemberResponse> getMember(@PathVariable Long userNo) {
		return ResponseEntity.ok(memberService.getMember(userNo));
	}

	//TODO: pathVar 나중에 토큰으로 교체
	@PatchMapping("/api/users/{userNo}")
	public ResponseEntity<Long> update(@PathVariable(value = "userNo") Long userNo,
		@RequestBody UpdateMemberRequest updateMemberRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.update(userNo, updateMemberRequest));
	}
}
