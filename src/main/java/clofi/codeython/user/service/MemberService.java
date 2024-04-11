package clofi.codeython.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clofi.codeython.common.domain.IllegalInputException;
import clofi.codeython.user.domain.Member;
import clofi.codeython.user.domain.request.CreateMemberRequest;
import clofi.codeython.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;

	public Long signUp(CreateMemberRequest createMemberRequest) {
		if (memberRepository.existsByNickname(createMemberRequest.getNickname())) {
			throw new IllegalInputException("이미 존재한 닉네임입니다.");
		}
		if (memberRepository.existsByUsername(createMemberRequest.getUsername())) {
			throw new IllegalInputException("이미 존재하는 아이디 입니다.");
		}
		//TODO: 시큐리티 적용 후 패스워드 인코딩 처리
		Member member = memberRepository.save(createMemberRequest.toMember());
		return member.getUserNo();
	}
}
