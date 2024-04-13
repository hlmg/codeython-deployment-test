package clofi.codeython.user.controller.response;

import clofi.codeython.user.domain.Member;

public record MemberResponse(
	String nickname,
	Integer level,
	Integer exp) {
	public static MemberResponse of(Member member) {
		return new MemberResponse(
			member.getNickname(),
			member.getLevel(),
			member.getExp()
		);
	}
}