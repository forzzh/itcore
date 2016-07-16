package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.Enum.CategroyEnum;
import www.core.com.Enum.LikeType;
import www.core.com.Enum.ModelEnum;
import www.core.com.annotation.MethodInfo;
@Entity
@Table(name="sysFoucusTopicorBook")
public class FoucusTopicorBook extends BaseCode{
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "char(10)")
	@MethodInfo(dataname = "likeType", label = "关注分类", isShow = "show", getEnum = "www.core.com.Enum.LikeType")
	private CategroyEnum categroyEnum;
	public CategroyEnum getCategroyEnum() {
		return categroyEnum;
	}
	public void setCategroyEnum(CategroyEnum categroyEnum) {
		this.categroyEnum = categroyEnum;
	}

	@Column
	public Integer commonId;
	
	@OneToOne
	@JoinColumn(name="createUserId")  
	@MethodInfo(dataname = "user.user", label = "关注", isShow = "show",detail=User.class)
	private User user;
	
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "char(10)")
	private ModelEnum model;
	public void setModel(ModelEnum model) {
		this.model = model;
	}
	public ModelEnum getModel() {
		return model;
	}
	public Integer getCommonId() {
		return commonId;
	}

	public void setCommonId(Integer commonId) {
		this.commonId = commonId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

		
	
}
