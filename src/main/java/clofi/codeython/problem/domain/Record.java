package clofi.codeython.problem.domain;

import clofi.codeython.common.domain.BaseEntity;
import clofi.codeython.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Record extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_no", nullable = false)
	private Long recordNo;

	@Column(name = "written_code", nullable = false, columnDefinition = "TEXT")
	private String writtenCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no", nullable = false)
	private Member userNo;

	@ManyToOne
	@JoinColumn(name = "problem_no", nullable = false)
	private Problem problemNo;

	@Column(name = "language", nullable = false, length = 20)
	private String language;

	@Column(name = "accuracy", nullable = false, columnDefinition = "int default 0")
	private int accuracy;

	@Column(name = "grade")
	private Integer grade;

	@Column(name = "member_cnt")
	private Integer memberCnt;

	public Record(String writtenCode, Member userNo, Problem problemNo, String language, int accuracy,
		Integer grade, Integer memberCnt) {
		this.writtenCode = writtenCode;
		this.userNo = userNo;
		this.problemNo = problemNo;
		this.language = language;
		this.accuracy = accuracy;
		this.grade = grade;
		this.memberCnt = memberCnt;
	}
}
