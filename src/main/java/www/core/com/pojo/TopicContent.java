
package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
@Entity
@Table(name="sysTopicContent")
@Indexed(index = "TopicContent")  
@Analyzer(impl=SmartChineseAnalyzer.class)//·Ö´ÊÆ÷  
public class TopicContent  extends BaseCode implements java.io.Serializable{

	@OneToOne
	@JoinColumn(name = "topicId")
	private Topic topic;

	@Field(store = Store.YES)  
	@Column(columnDefinition = "char(30)")
	private String title;
	
	@Type(type="text")  
	private String markdown;
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		 return BaseCode.clean(title);
	}
	public void setMarkdown(String markdown) {
		this.markdown = markdown;
	}
	public String getMarkdown() {
		return markdown;
	}
	@Field(store = Store.YES)  
	@Type(type="text")  

	private String content;
	@Field(store = Store.YES)  
	@Column
	private Integer type =0;
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getType() {
		return type;
	}
	
	public String getContent() {
		 return BaseCode.cleanScript (this.content);
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}


}
