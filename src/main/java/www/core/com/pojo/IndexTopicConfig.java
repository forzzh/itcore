package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.annotation.MethodInfo;
import www.core.com.annotation.TableInfo;

/**
 * 首页帖子置顶列表
 * @author yinnannan
 *
 */

@Entity
@TableInfo(defend = "defend", packageName = IndexTopicConfig.class )
@Table(name="sysIndexTopicConfig")
public class IndexTopicConfig extends BaseCode{

	@OneToOne
	@JoinColumn(name = "topicId")
	@MethodInfo(dataname = "topic.title", label = "标题名称", isShow = "show")
	private Topic  topic; 
	
	@Column(columnDefinition="INT default 0")
	@MethodInfo(dataname = "sort", label = "顺位", isShow = "show")
	private Integer sort =0;
	
	@Column
	private String description;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getSort() {
		return sort;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public Topic getTopic() {
		return topic;
	}
}
