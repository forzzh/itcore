package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.Enum.LikeType;
@Entity
@Table(name="syslike")
public class Like  extends BaseCode{
	@Column(columnDefinition="INT default 0")
	private Integer parm1;
	
	@OneToOne
	@JoinColumn(name="createUserId")  
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "char(10)")
	LikeType type;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LikeType getType() {
		return type;
	}
	public void setType(LikeType type) {
		this.type = type;
	}
	public void setParm1(Integer parm1) {
		this.parm1 = parm1;
	}
	public Integer getParm1() {
		return parm1;
	}
}
