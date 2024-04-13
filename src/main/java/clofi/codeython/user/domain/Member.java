package clofi.codeython.user.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import clofi.codeython.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_no", nullable = false)
	private Long userNo;

	@Column(name = "username", nullable = false, length = 20)
	private String username;

	@Column(name = "password", nullable = false, length = 128)
	private String password;

	@Column(name = "nickname", nullable = false, length = 10)
	private String nickname;

	@Column(name = "level", nullable = false, columnDefinition = "int default 1")
	private Integer level;

	@Column(name = "exp", nullable = false, columnDefinition = "int default 0")
	private Integer exp;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	public Member(String username, String password, String nickname) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}

	public void updateNickName(String nickname) {
		this.nickname = nickname;
	}
}
