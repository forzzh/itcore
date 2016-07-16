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
@TableInfo(defend = "defend", packageName = IndexLabelConfig.class )
@Entity
@Table(name="sysIndexLabelConfig")
public class IndexLabelConfig  extends BaseCode{

	@OneToOne
	@MethodInfo(dataname = "label.name", label = "标签名称", isShow = "show")
	@JoinColumn(name = "labelId")
	private Label  label; 
	
	@Column(columnDefinition="INT default 0")
	@MethodInfo(dataname = "sort", label = "顺位", isShow = "show")
	private Integer sort =0;
	
	public void setLabel(Label label) {
		this.label = label;
	}
	public Label getLabel() {
		return label;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getSort() {
		return sort;
	}

}
