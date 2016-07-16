package www.core.com.pojo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sys_publicsign_likeItlist")
public class PubSignLikeList extends BaseCode {
	@OneToOne
	@JoinColumn(name = "userIdWhoLikeIt")
	private User userWhoLikeIt;

	@OneToOne
	@JoinColumn(name = "pubSignIdlikeWhat")
	private PublishSign pubSignIdlikeWhat;
	
	public User getUserWhoLikeIt() {
		return userWhoLikeIt;
	}

	public void setUserWhoLikeIt(User userWhoLikeIt) {
		this.userWhoLikeIt = userWhoLikeIt;
	}

	public PublishSign getPubSignIdlikeWhat() {
		return pubSignIdlikeWhat;
	}

	public void setPubSignIdlikeWhat(PublishSign pubSignIdlikeWhat) {
		this.pubSignIdlikeWhat = pubSignIdlikeWhat;
	}

}
