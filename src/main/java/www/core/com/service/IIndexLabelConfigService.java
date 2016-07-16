package www.core.com.service;

import java.util.List;

import www.core.com.pojo.IndexLabelConfig;
import www.core.com.pojo.IndexTopicConfig;
import www.core.com.pojo.Message;

public interface IIndexLabelConfigService {
	
	public Message<IndexLabelConfig> setLabelRange(Integer labelId ,Integer range);
	public List<IndexLabelConfig>  getLabelRangeList(Integer start , Integer row);
}
