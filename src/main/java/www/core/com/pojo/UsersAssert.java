package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.annotation.MethodInfo;

@Entity
@Table(name="sysusersassert")
public class UsersAssert extends BaseCode {

	
	@OneToOne
	@JoinColumn(name="createUserId")  
	private User user;
	@Column(nullable=false,columnDefinition="double default 0.0")	//ÔÄ¶ÁÁ¿
	@MethodInfo(dataname = "hits", label = "»ý·Ö", isShow = "show")
	private Double score=0.0;
	
	public void setScore(Double score) {
		this.score = score;
	}
	public User getUser() {
		return user;
	}
	public Double getScore() {
		return score;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
