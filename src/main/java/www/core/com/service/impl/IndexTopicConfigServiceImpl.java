package www.core.com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.core.com.pojo.IndexTopicConfig;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.service.IIndexTopicConfigService;
import www.core.com.service.ITopicService;

@Service("indexTopicConfigService")
public class IndexTopicConfigServiceImpl extends AbstractService<IndexTopicConfig>implements IIndexTopicConfigService {
	@Autowired
	ITopicService topicService;

	IndexTopicConfig getIndexTopicConfigByTopicId(Integer TopicId) {
		IndexTopicConfig result = getById(IndexTopicConfig.class, TopicId);
		if (result == null) {
			result = new IndexTopicConfig();
		}
		return result;
	}

	@Override
	public Message<IndexTopicConfig> setTopicRange(Integer topicId, Integer range, String description) {
		Message<IndexTopicConfig> indexMsg = new Message<>();
		Topic oldTopic = topicService.getTopicById(topicId);
		if (oldTopic == null) {
			indexMsg.setMsg("帖子不存在");
			indexMsg.setStatus(false);
			return indexMsg;
		}
		IndexTopicConfig indexTopicConfig = getIndexTopicConfigByTopicId(topicId);
		indexTopicConfig.setTopic(oldTopic);
		indexTopicConfig.setSort(range);
		indexTopicConfig.setDescription(description);

		saveOrUpdate(indexTopicConfig);

		indexMsg.setMsg("设置成功");
		indexMsg.setStatus(true);

		return indexMsg;

	}

	/* 
	 * 获取置顶的帖子信息
	 *  
	 */
	@Override
	public List getTopicRangeList(Integer start, Integer row) {
		Message<IndexTopicConfig> indexMsg = new Message<>();
		List topics = hibernateDAO.getListByPageByObject(
				" from IndexTopicConfig itc order by itc.sort", null, start, row);
		
		return topics;
	}

}
