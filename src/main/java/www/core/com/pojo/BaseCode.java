package www.core.com.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import www.core.com.annotation.MethodInfo;

@MappedSuperclass
public abstract class BaseCode implements Serializable {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@MethodInfo(dataname = "user", label = "�ǳ�", isShow = "show")
	private Integer id;

	@Column
	@MethodInfo(dataname = "createDate", label = "����ʱ��", isShow = "show", issearch = false)
	private Timestamp createDate = new Timestamp(System.currentTimeMillis());

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static String clean(String value) {
		value = value.replaceAll("<script", "&lt;script").replaceAll("script>", "script&gt;");
		value = value.replaceAll("<input", "&lt;input");
		
//		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
//		value = value.replaceAll("'", "&#39;");
//		value = value.replaceAll("eval\\((.*)\\)", "");
//		value = value.replaceAll("[\\\"<a>\\\'][\\s]*javascript:(.*)[\\\"\\\'</a>]", "\"\"");
//		value = value.replaceAll("script", "");
		return value;
	}
	
	public static String cleanScript(String value) {
		value = value.replaceAll("<script", "&lt;script").replaceAll("script>", "script&gt;");
		
//		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
//		value = value.replaceAll("'", "&#39;");
//		value = value.replaceAll("eval\\((.*)\\)", "");
//		value = value.replaceAll("[\\\"<a>\\\'][\\s]*javascript:(.*)[\\\"\\\'</a>]", "\"\"");
//		value = value.replaceAll("script", "");
		return value;
	}
}
