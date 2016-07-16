package www.core.com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.lucene.queryParser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.Enum.CategroyEnum;
import www.core.com.dao.BaseDAO;
import www.core.com.pojo.Group;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.pojo.TopicContent;
import www.core.com.pojo.User;
import www.core.com.service.IGroupPersonService;
import www.core.com.service.IGroupService;
import www.core.com.service.IIndexBookConfigService;
import www.core.com.service.IIndexLabelConfigService;
import www.core.com.service.IIndexService;
import www.core.com.service.IIndexTopicConfigService;
import www.core.com.service.ILabelService;
import www.core.com.service.ITopicService;
import www.core.com.service.IUserRelationshipService;
import www.core.com.utils.Page;

@Transactional(propagation=Propagation.NOT_SUPPORTED)
@Service
public class IndexService implements IIndexService {
	
	@Resource
	BaseDAO hibernateDAO;

	@Resource
	ITopicService topicService;
	
	@Resource
	ILabelService labelService;
	
	@Resource
	IGroupService groupService;
	
	@Resource
	IGroupPersonService groupPersonService;
	
	@Resource
	IIndexLabelConfigService indexLabelConfigService;
	
	@Resource
	IIndexTopicConfigService indexTopicConfigService;
	
	@Resource
	IIndexBookConfigService indexBookConfigService;
	
	@Resource
	IUserRelationshipService userRelationshipService;
	
	/**
	 * 加载首页数据
	 */
	@Override
	public Map index(User user) {
		Map map = new HashMap();
		//软件发布帖子
		List topics = topicService.getTopics(CategroyEnum.project, 0, 6);
		map.put("topics", topics);
		
		//技术问题帖子
		List questions = topicService.getTopics(CategroyEnum.question, 0, 7);
		map.put("questions", questions);
		
		//标签
		List labels = labelService.getRandomLabelList(20);
		map.put("labels", labels);
		
		//index标签
		List indexLabels = indexLabelConfigService.getLabelRangeList(-1,0);
		map.put("indexLabels", indexLabels);
		
		if(indexLabels.size() == 0){
			List newIndexLabels =  labelService.queryLabelData(0,7);
			map.put("newIndexLabels", newIndexLabels);
		}
		
		List<Topic> focusTopics = null;
		
		//获取登陆用户的信息
		if(user != null){
			//用户圈子信息
			List<Group> groups = groupPersonService.getGroupByUser(user.getId());
			map.put("userGroup", groups);
			
			//用户所关注人的帖子
			Message<User> followerListMsg = userRelationshipService.getFollowerList(user.getId(), 0, 10);
			List<User> followerList = followerListMsg.getList();
			if (followerList != null && followerList.size() > 0) {
				Integer[] userIds = new Integer[followerList.size()];
				for (int i = 0, len = followerList.size(); i < len; i++) {
					userIds[i] = followerList.get(i).getId();
				}
				focusTopics = topicService.getTopicListByUsers(userIds, 0, 10);
				map.put("focusTopics", focusTopics);
			} else {
				focusTopics = topicService.getHotTopics(10);
				map.put("focusTopics", focusTopics);
			}
			
			
		}
		
		//如果没关注的帖子，index帖子
		if (focusTopics == null) {
			List indexTopics = indexTopicConfigService.getTopicRangeList(-1, 0);
			map.put("indexTopics", indexTopics);
			
			if(indexTopics.size() == 0){
				List newIndexTopic = topicService.getHotTopics(10);
				map.put("newIndexTopics", newIndexTopic);
			}
		}
		
		
		//index图书
		List indexBooks = indexBookConfigService.getBookRangeList(-1, 0);
		map.put("indexBooks", indexBooks);
		
		//帖子类型
		CategroyEnum[] categroyEnums = CategroyEnum.values();
		map.put("categroyEnums", categroyEnums);
		
		
		
		return map;
	}

	@Transactional
	@Override
	public Message indexSearch(String search) {
		Message message = new Message();
		
		try {
			Page p1 = hibernateDAO.getSearch(TopicContent.class, 1, search, "title", "content");
			Page p2 = hibernateDAO.getSearch(User.class, 1, search, "chinaName", "user","email");
			message.setStatus(true);
			Map map = new HashMap();
			map.put("topics", p1.getList());
			map.put("users", p2.getList());
			message.setData(map);
			return message;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		message.setStatus(false);
		message.setMsg("查询出错!");
		return message;
	}

}
