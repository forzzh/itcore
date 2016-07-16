package www.core.com.pojo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.Enum.ScoreType;

@Entity
@Table(name="sysscorelog")
public class ScoreLog  extends BaseCode{

	
	//格式yyyy-mm-dd 不需要时分秒
	@Column
	private Date date;
	
	@Column(columnDefinition = "char(30)")
	private ScoreType score;
	
	@OneToOne
	@JoinColumn(name="createUserId")  
	private User user;
	
	@Column(columnDefinition = "char(30)")
	private Integer number;
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	
	public void setScore(ScoreType score) {
		this.score = score;
	}
	public ScoreType getScore() {
		return score;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
}
