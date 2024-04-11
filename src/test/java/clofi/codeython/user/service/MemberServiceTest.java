package clofi.codeython.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import clofi.codeython.common.domain.IllegalInputException;
import clofi.codeython.user.domain.Member;
import clofi.codeython.user.domain.request.CreateMemberRequest;
import clofi.codeython.user.repository.MemberRepository;

@SpringBootTest
class MemberServiceTest {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;

	@AfterEach
	void tearDown() {
		memberRepository.deleteAllInBatch();
	}

	@DisplayName("닉네임, 아이디, 비밀번호를 입력하면 회원가입을 할 수 있다.")
	@Test
	void signUpTest() {
		//given
		CreateMemberRequest createMemberRequest = new CreateMemberRequest(
			"zeno",
			"zeno1030",
			"wkrwjs5763!"
		);
		//when
		Long memberId = memberService.signUp(createMemberRequest);
		Member member = memberRepository.findAllByUserNo(memberId);
		//then

		assertThat(member.getUsername()).isEqualTo("zeno1030");
		assertThat(member.getNickname()).isEqualTo("zeno");
		assertThat(member.getLevel()).isEqualTo(1);
		assertThat(member.getExp()).isEqualTo(0);
	}

	@DisplayName("닉네임이 겹칠 시 예외가 발생한다.")
	@Test
	void signUpExceptionTest() {
		//given
		Member member = new Member(
			"rnfmal",
			"wl3648",
			"zeno"
		);
		memberRepository.save(member);

		CreateMemberRequest createMemberRequest = new CreateMemberRequest(
			"zeno",
			"zeno1030",
			"wkrwjs5763!"
		);
		//when //then
		assertThatThrownBy(() ->
			memberService.signUp(createMemberRequest)).isInstanceOf(IllegalInputException.class)
			.hasMessage("이미 존재한 닉네임입니다.");
	}

	@DisplayName("아이디가 겹칠 시 예외가 발생한다.")
	@Test
	void signUpIdExceptionTest() {
		//given
		Member member = new Member(
			"zeno1030",
			"wl3648",
			"rnfmal"
		);
		memberRepository.save(member);

		CreateMemberRequest createMemberRequest = new CreateMemberRequest(
			"zeno",
			"zeno1030",
			"wkrwjs5763!"
		);
		//when //then
		assertThatThrownBy(() ->
			memberService.signUp(createMemberRequest)).isInstanceOf(IllegalInputException.class)
			.hasMessage("이미 존재하는 아이디 입니다.");
	}
}