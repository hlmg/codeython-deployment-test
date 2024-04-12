package clofi.codeython.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import clofi.codeython.user.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUserNo(Long userNo);

	Boolean existsByNickname(String nickName);

	Boolean existsByUsername(String userName);
}
