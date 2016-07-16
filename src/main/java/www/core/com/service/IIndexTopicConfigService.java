package www.core.com.service;

import java.util.List;

import www.core.com.pojo.IndexTopicConfig;
import www.core.com.pojo.Message;

public interface IIndexTopicConfigService {
	public Message<IndexTopicConfig> setTopicRange(Integer topicId ,Integer range,String description);
	public List<IndexTopicConfig> getTopicRangeList(Integer start , Integer row);

}
