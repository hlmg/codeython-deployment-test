package clofi.codeython.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clofi.codeython.user.controller.response.MemberResponse;
import clofi.codeython.user.domain.Member;
import clofi.codeython.user.domain.request.CreateMemberRequest;
import clofi.codeython.user.domain.request.UpdateMemberRequest;
import clofi.codeython.user.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;

	public Long signUp(CreateMemberRequest createMemberRequest) {
		if (memberRepository.existsByNickname(createMemberRequest.getNickname())) {
			throw new IllegalArgumentException("이미 존재한 닉네임입니다.");
		}
		if (memberRepository.existsByUsername(createMemberRequest.getUsername())) {
			throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
		}
		//TODO: 시큐리티 적용 후 패스워드 인코딩 처리
		Member member = memberRepository.save(createMemberRequest.toMember());
		return member.getUserNo();
	}

	public Long update(Long userNo, UpdateMemberRequest updateMemberRequest) {
		Member member = memberRepository.findByUserNo(userNo)
			.orElseThrow(() -> new EntityNotFoundException("일치하는 사용자가 없습니다."));
		if (memberRepository.existsByNickname(updateMemberRequest.getNickname())) {
			throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
		}
		member.updateNickName(updateMemberRequest.getNickname());
		return member.getUserNo();
	}

	public MemberResponse getMember(Long userNo) {
		return memberRepository.findByUserNo(userNo)
			.map(MemberResponse::of)
			.orElseThrow(() -> new EntityNotFoundException("일치하는 사용자가 없습니다."));
	}
}
