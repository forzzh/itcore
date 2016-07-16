package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import www.core.com.Enum.ScoreType;

@Entity
@Table(name="sysscoreconfig")
public class ScoreConfig  extends BaseCode{
	@Column(columnDefinition = "char(30)")
	private ScoreType score;
	
	@Column
	private Integer  first;
	
	@Column
	private Integer common;

	public ScoreType getScore() {
		return score;
	}

	public void setScore(ScoreType score) {
		this.score = score;
	}

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getCommon() {
		return common;
	}

	public void setCommon(Integer common) {
		this.common = common;
	}
	
}
