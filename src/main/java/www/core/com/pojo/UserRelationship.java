package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.Enum.UserRelationshipEnum;

@Entity
@Table(name = "sysUserRelationship")
public class UserRelationship extends BaseCode {

	/**
	 * �����û����类��ע
	 */
	@OneToOne
	@JoinColumn(name = "passiveUserId")
	private User passiveUser;

	/**
	 * �����û������˿
	 */
	@OneToOne
	@JoinColumn(name = "positiveUserId")
	private User positiveUser;


	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "char(15)")
	UserRelationshipEnum relationshipType;

	public User getPassiveUserId() {
		return passiveUser;
	}


	public void setPassiveUserId(User passiveUser) {
		this.passiveUser = passiveUser;
	}


	public User getPositiveUserId() {
		return positiveUser;
	}


	public void setPositiveUserId(User positiveUser) {
		this.positiveUser = positiveUser;
	}


	public UserRelationshipEnum getRelationshipType() {
		return relationshipType;
	}


	public void setRelationshipType(UserRelationshipEnum relationshipType) {
		this.relationshipType = relationshipType;
	}


}
