package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.Enum.*;

@Entity
@Table(name="systopicanser")
public class TopicAnser extends BaseCode {
	
	@OneToOne
	@JoinColumn(name="topicid")
	private Topic topic;//����
	
	@Column
	private CategroyEnum CategroyEnum;//��������
	
	@OneToOne
	@JoinColumn(name="userid")
	private User user;//�������û�
	
	@OneToOne
	@JoinColumn(name="replyid")
	private Reply reply;//����ظ�

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public CategroyEnum getCategroyEnum() {
		return CategroyEnum;
	}

	public void setCategroyEnum(CategroyEnum categroyEnum) {
		CategroyEnum = categroyEnum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Reply getReply() {
		return reply;
	}

	public void setReply(Reply reply) {
		this.reply = reply;
	}
	
}
