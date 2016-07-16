package www.core.com.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ocpsoft.prettytime.PrettyTime;

import www.core.com.Enum.ReplyEnum;

@Entity
@Table(name="sysreply")
public class Reply extends BaseCode{
	
	@Column
	private String content;//回复内容
	@Column(columnDefinition="INT default 0")
	private Integer share =0;//赞数
	
	@Column
	private Integer replyID;//....用户回复帖子或book的主键id

	@Enumerated(EnumType.STRING)
	private ReplyEnum replyEnum;//回复类型
	
	@OneToOne
	@JoinColumn(name="userid")  
	private User user;//回复用户id
	@Transient
	private String showData;
	@OneToOne
	@JoinColumn(name="groupId")  
	private Group group;
	public void setGroup(Group group) {
		this.group = group;
	}
	public Group getGroup() {
		return group;
	}
	public String getShowData() {
		PrettyTime p = new PrettyTime(new Date());
		return p.format(this.getCreateDate());
	}
	public void setShowData(String showData) {
		this.showData = showData;
	}
	public String getContent() {
		return super.clean(this.content).replace("<audio", "	&#60;audio").replace("<script", "&62;audio");
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getShare() {
		return share;
	}

	public void setShare(Integer share) {
		this.share = share;
	}

	public ReplyEnum getReplyEnum() {
		return replyEnum;
	}

	public void setReplyEnum(ReplyEnum replyEnum) {
		this.replyEnum = replyEnum;
	}

	public Integer getReplyID() {
		return replyID;
	}

	public void setReplyID(Integer replyID) {
		this.replyID = replyID;
	}

}
