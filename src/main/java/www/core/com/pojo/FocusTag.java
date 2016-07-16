package www.core.com.pojo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sysFousTag")
public class FocusTag extends BaseCode{

	@OneToOne
	@JoinColumn(name = "labelId")
	private Label label;

	@OneToOne
	@JoinColumn(name = "fousUserId")
	private User user;
	public void setLabel(Label label) {
		this.label = label;
	}
	public Label getLabel() {
		return label;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public FocusTag(Label label, User user) {
		super();
		this.label = label;
		this.user = user;
	}
	public FocusTag() {
	}
}
