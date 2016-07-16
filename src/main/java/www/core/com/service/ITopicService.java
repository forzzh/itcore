
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
	 * ��ȡ��ͼ��������hits��������
	 * @param num	��ȡ����
	 * @return
	 */
	public List getHotTopics(Integer num);
	
	/**
	 * ��ȡ�û���������
	 * @param userId	�û�id
	 * @param start		��ʼ����
	 * @param row		��ʾ����
	 * @return
	 */
	public List getUserTopics(Integer userId, Integer start, Integer row);
	
	void incReadHit(Integer topicId);
	/**
	 * �����������ͻ���ȶ���ߵļ�������
	 * @param categroyEnum  ��������
	 * @param start         
	 * @param row
	 * @return
	 */
	List<Topic> getHotTopicByCategroyName(CategroyEnum categroyEnum,Integer start, Integer row);

	/**
	 * ����Ȧ��id��ȡ����
	 * @param groupId
	 * @param start
	 * @param count
	 * @return
	 */
	public Message<List<Topic>> getTopicsByGroupId(Integer groupId,
			Integer start, Integer count, CategroyEnum categroyEnum);
	
	/**
	 * ����label id��ȡ����
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
	 * 		�û�����
	 * @param first
	 * @param count
	 * @return
	 */
	public List<Topic> getTopicListByUsers(Integer[] userIds, Integer first, Integer count);
}
