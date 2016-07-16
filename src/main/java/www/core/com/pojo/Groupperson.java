package www.core.com.pojo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="sysgroupperson")
public class Groupperson extends BaseCode {
	//ֻ�����ͨ�����߲���Ҫ��˵� ���ݿ��Խ�����
	//group �趨 noagree ��ֱ�� groupperson��� ����messagetype�Ӹ���Ϣ ��Ϣ��userid����Ȧ��Ⱥ������

	
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
