package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;

import www.core.com.annotation.MethodInfo;
import www.core.com.annotation.TableInfo;

@TableInfo(defend = "defend", packageName = Label.class )
@Entity
@Table(name = "syslabel")
public class Label extends BaseCode{

	@Field(store = Store.YES)  
	@Column(columnDefinition = "char(20)")
	@MethodInfo(dataname = "name", label = "标签名称", isShow = "show")
	private String name;
	
	@Column(columnDefinition = "char(200)")
	@MethodInfo(dataname = "description", label = "标签详情", isShow = "show")
	private String description;
	
	@Column(columnDefinition="INT default 0") // 关注人数
	@MethodInfo(dataname = "foucsPerson", label = "关注此标签的人数", isShow = "show")
	private Integer foucsPerson =0;
	
	@Column(columnDefinition="INT default 0") //帖子数量
	@MethodInfo(dataname = "topics", label = "贴上此标签的帖子数", isShow = "show")
	private Integer topics =0;
	
	@Column(columnDefinition="INT default 0")
	@MethodInfo(dataname = "foucsGroup", label = "贴上此标签的圈子数", isShow = "show")
	private Integer foucsGroup =0;
	
	@Column(columnDefinition="INT default 0")
	@MethodInfo(dataname = "foucsBook", label = "贴上此标签的书籍数", isShow = "show")
	private Integer foucsBook =0;
	@Transient
	private String showData="未关注";
	@OneToOne
	@JoinColumn(name = "user")
	private User user;
	@Column(columnDefinition="INT default 0")
	@MethodInfo(dataname = "score", label = "热度", isShow = "show")
	private Double score =0.0;
	public void setShowData(String showData) {
		this.showData = showData;
	}
	public String getShowData() {
		return showData;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Double getScore() {
		return score;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	
	public Integer getFoucsBook() {
		return foucsBook;
	}public void setFoucsBook(Integer foucsBook) {
		this.foucsBook = foucsBook;
	}
	
	public Integer getFoucsGroup() {
		return foucsGroup;
	}
	public void setFoucsGroup(Integer foucsGroup) {
		this.foucsGroup = foucsGroup;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getFoucsPerson() {
		return foucsPerson;
	}
	public void setFoucsPerson(Integer foucsPerson) {
		this.foucsPerson = foucsPerson;
	}
	public Integer getTopics() {
		return topics;
	}
	public void setTopics(Integer topics) {
		this.topics = topics;
	}
	public Label(String name,Integer id,Integer foucsPerson) {
		super();
		this.name = name;
		this.foucsPerson=foucsPerson;
		super.setId(id);
	}
	public Label() {
		// TODO Auto-generated constructor stub
	}
}
