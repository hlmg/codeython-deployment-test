package clofi.codeython.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import clofi.codeython.user.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findAllByUserNo(Long userNo);

	Boolean existsByNickname(String nickName);

	Boolean existsByUsername(String userName);
}
