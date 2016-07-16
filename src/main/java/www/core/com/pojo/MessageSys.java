package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.Enum.MessageType;
import www.core.com.Enum.StatusEnum;

@Entity
@Table(name="messagesys")
public class MessageSys extends BaseCode{


	@Column(columnDefinition = "char(200)")
	private String content;
	@Column(columnDefinition = "char(10)")//  如果参加圈子  parm1 是用户id parm2是圈子id
	private String parm1;
	@Column(columnDefinition = "char(10)")
	private String parm2;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "char(20)")
	MessageType type;
	@OneToOne
	@JoinColumn(name="userId")  
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "char(10)")
	private StatusEnum status;
	
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getContent() {
		return super.clean(this.content);
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getParm1() {
		return parm1;
	}
	public void setParm1(String parm1) {
		this.parm1 = parm1;
	}
	public String getParm2() {
		return parm2;
	}
	public void setParm2(String parm2) {
		this.parm2 = parm2;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	
}
