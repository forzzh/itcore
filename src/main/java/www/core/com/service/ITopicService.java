
package www.core.com.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.queryParser.ParseException;

import www.core.com.Enum.*;
import www.core.com.pojo.Group;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.pojo.TopicContent;
import www.core.com.pojo.User;
import www.core.com.utils.Page;

public interface ITopicService {
	public Topic getTopicById(Integer topicId);

	public Message<Topic> add(String qiniu, String staticPath, Topic topic, TopicContent detail, User user,
			String... label) throws InterruptedException, IOException, Exception;

	public Message<Topic> detail(Integer id);
	
	public Message<Topic> detailMarkdown(Integer id);

	public Map searchAll(String search, Integer pageindex) throws ParseException;
	
	public Page search(String search, Integer pageindex) throws ParseException;

	public Message<Topic> update(String staticPath,String content, Integer id,String title,String markdown) throws Exception;

	public Message<Topic> addReply(Integer id, Integer groupid ,User replyUser, ReplyEnum replyEnum, String content,
			CategroyEnum categroyEnum);
	
	public Message<Topic> getTopicsWhoCreate(CategroyEnum categroyEnum,Integer userId,Integer start, Integer row);

	public Message<Topic> addTopicAnser(Integer topicId, Integer replyId);
	
	public List<Topic> getTopics(CategroyEnum categroyEnum,Integer start, Integer row);
	
	/**
	 * 获取非图书帖子中hits最多的帖子
	 * @param num	获取数量
	 * @return
	 */
	public List getHotTopics(Integer num);
	
	/**
	 * 获取用户所有帖子
	 * @param userId	用户id
	 * @param start		起始条数
	 * @param row		显示条数
	 * @return
	 */
	public List getUserTopics(Integer userId, Integer start, Integer row);
	
	void incReadHit(Integer topicId);
	/**
	 * 根据帖子类型获得热度最高的几个帖子
	 * @param categroyEnum  帖子类型
	 * @param start         
	 * @param row
	 * @return
	 */
	List<Topic> getHotTopicByCategroyName(CategroyEnum categroyEnum,Integer start, Integer row);

	/**
	 * 根据圈子id获取帖子
	 * @param groupId
	 * @param start
	 * @param count
	 * @return
	 */
	public Message<List<Topic>> getTopicsByGroupId(Integer groupId,
			Integer start, Integer count, CategroyEnum categroyEnum);
	
	/**
	 * 根据label id获取帖子
	 * @param labelId
	 * @param start
	 * @param count
	 * @return
	 */
	public Message<List<Topic>> getTopicsByLabelId(Integer labelId,
			Integer start, Integer count, CategroyEnum categroyEnum);


	public Message<Topic> deleteTopid(User user, Integer topicId);

	/**
	 * 
	 * @param userIds
	 * 		用户集合
	 * @param first
	 * @param count
	 * @return
	 */
	public List<Topic> getTopicListByUsers(Integer[] userIds, Integer first, Integer count);
}
