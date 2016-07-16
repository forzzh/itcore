package www.core.com.pojo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="sysgroupperson")
public class Groupperson extends BaseCode {
	//只有审核通过或者不需要审核的 数据可以进来的
	//group 设定 noagree 就直接 groupperson入库 并且messagetype加个消息 消息的userid就是圈子群创建人

	
	@OneToOne
	@JoinColumn(name="userId")  
	private User user;
	@OneToOne
	@JoinColumn(name="groupId")
	private Group group;
	
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
}
