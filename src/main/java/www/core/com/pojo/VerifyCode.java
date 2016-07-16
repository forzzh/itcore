package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.Enum.VerifyCodeEnum;
import www.core.com.Enum.VerifyCodeTypeEnum;

@Entity
@Table(name="sysVerifyCode")
public class VerifyCode extends BaseCode {
	@OneToOne
	@JoinColumn(name="userId")  
	private User user;
	
	@Column(columnDefinition = "char(50)")
	private String code;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "char(10)")
	private VerifyCodeEnum status;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "char(15)")
	VerifyCodeTypeEnum type;
	
	public VerifyCodeTypeEnum getType() {
		return type;
	}
	public void setType(VerifyCodeTypeEnum type) {
		this.type = type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public VerifyCodeEnum getStatus() {
		return status;
	}
	public void setStatus(VerifyCodeEnum status) {
		this.status = status;
	}
	
}
