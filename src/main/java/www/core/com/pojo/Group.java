package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.Enum.JoinTypeEnum;
import www.core.com.Enum.StatusEnum;
import www.core.com.annotation.MethodInfo;
import www.core.com.annotation.TableInfo;

@TableInfo(defend = "defend", packageName = Group.class )
@Entity
@Table(name = "sysgroup")
public class Group extends BaseCode {



	@Column(columnDefinition = "char(20)",unique=true)
	@MethodInfo(dataname = "name", label = "Ȧ������", isShow = "show")
	private String name;
	
	@Column(columnDefinition = "char(200)")
	@MethodInfo(dataname = "image", label = "ͼ���ַ", isShow = "show")
	private String image;
	
	@Column(columnDefinition = "char(200)")
	@MethodInfo(dataname = "description", label = "��������", isShow = "show")
	private String description;

	@Column(columnDefinition="INT default 0") // Ȧ������
	@MethodInfo(dataname = "foucsPersonNum", label = "��ע����", isShow = "show")
	private Integer foucsPersonNum;
	@Column(columnDefinition="INT default 0") // ��������
	private Integer topicsNum;

	@OneToOne
	@JoinColumn(name = "labelId")
	@MethodInfo(dataname = "label.name",  label = "��ǩ", isShow = "show",detail=Label.class)
	private Label label;

	@Column(columnDefinition="INT default 0")
	@MethodInfo(dataname = "score", label = "�ȶ�", isShow = "show")
	private Double score;

	// �Ƿ�ɾ��
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "char(10)")
	@MethodInfo(dataname = "status", label = "״̬", isShow = "show", getEnum = "www.core.com.Enum.StatusEnum")
	private StatusEnum status;

	// �Ƿ񿪷ż���
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "char(10)")
	@MethodInfo(dataname = "joinType", label = "����״̬", isShow = "show", getEnum = "www.core.com.Enum.JoinTypeEnum")
	private JoinTypeEnum joinType;
	
	@Column
	@MethodInfo(dataname = "extLabels", label = "��չ��ǩ", isShow = "show")
	private String extLabels;
	


	@OneToOne
	@JoinColumn(name = "userId")
	private User user;
	@Column(columnDefinition = "char(200)")
	private String document;
	public void setDocument(String document) {
		this.document = document;
	}
	public String getDocument() {
		return document;
	}
	
	public String getExtLabels() {
		return extLabels;
	}

	public void setExtLabels(String extLabels) {
		this.extLabels = extLabels;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Group() {
	}

	public Group(Integer id) {
		super.setId(id);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public JoinTypeEnum getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinTypeEnum joinType) {
		this.joinType = joinType;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	public Integer getTopicsNum() {
		return topicsNum;
	}

	public void setTopicsNum(Integer topicsNum) {
		this.topicsNum = topicsNum;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getFoucsPersonNum() {
		return foucsPersonNum;
	}

	public void setFoucsPersonNum(Integer foucsPersonNum) {
		this.foucsPersonNum = foucsPersonNum;
	}
}
