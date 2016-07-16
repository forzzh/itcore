package www.core.com.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ocpsoft.prettytime.PrettyTime;

@Entity
@Table(name = "syspublicsign")
public class PublishSign extends BaseCode {

	@OneToOne
	@JoinColumn(name = "createUserId")
	private User createUser;

	/**
	 * ��������
	 */
	@Column(columnDefinition = "char(200)")
	private String content;


	/**
	 * ������
	 */
	@Column(columnDefinition="INT default 0")
	private Integer likesNum;//��Ȼ����ȡ��Ϊlike,���SQL��like���Ū��,����ִ�г���
	
	@Transient
	private String showData;

	
	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLike() {
		return likesNum;
	}

	public void setLike(Integer like) {
		this.likesNum = like;
	}

	public String getShowData() {
		PrettyTime p = new PrettyTime(new Date());
		return p.format(this.getCreateDate());
	}

	public void setShowData(String showData) {
		this.showData = showData;
	}
	
}
