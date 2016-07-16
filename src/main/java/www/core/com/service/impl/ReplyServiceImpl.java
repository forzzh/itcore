package www.core.com.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import www.core.com.Enum.ReplyEnum;
import www.core.com.dao.BaseDAO;
import www.core.com.pojo.Group;
import www.core.com.pojo.Message;
import www.core.com.pojo.Reply;
import www.core.com.pojo.Topic;
import www.core.com.pojo.TopicAnser;
import www.core.com.service.IReplyService;
import www.core.com.service.ITopicService;

@Service("replyService")
public class ReplyServiceImpl extends AbstractService<Reply> implements IReplyService {
	
	@Resource
	ITopicService topicService;
	
	@Resource(name = "hibernateDAO")
	BaseDAO hibernateDAO;

	@Override
	public Message<Reply> queryReplyByReplyID(Integer replyID,Integer pageNum,Integer count,ReplyEnum replyEnum,Integer answerid){
		Message<Reply> result = new Message<>();
		Map<String,Object> params = new HashMap<>();
		
		String hql = null;
		if(answerid == null){
			hql = " from " + Reply.class.getSimpleName() + " reply where replyID = :replyID and replyEnum =:replyEnum order by createDate desc";
		}else{
			params.put("answerid", answerid);
			hql = " from " + Reply.class.getSimpleName() + " reply where replyID = :replyID and replyEnum =:replyEnum and id != :answerid order by createDate desc";
		}

		params.put("replyID", replyID);
		params.put("replyEnum", replyEnum);

		try{
			int start ;
			if(pageNum == 1){
				start = 0;
			}else{
				start = (pageNum-1)*(count-1);
			}
			List<Reply> replyList = hibernateDAO.getListByPageByObject(hql, params, start, count);
			result.setList(replyList);
			result.setStatus(true);
		}catch(Exception e){
			result.setMsg("查询错误:" + e.getMessage());
			result.setBasic(false);
		}
		return result;
	}
	
	/**
	 * 查询用户所有帖子回复
	 * @param userid
	 * @return
	 */
	public Map queryUserAllReply(Integer userid,int start,int row){
		Map map = new HashMap();
		map.put("userid", userid);
		
		String hql = " from " + Reply.class.getSimpleName() + " reply where reply.user.id = :userid order by createDate desc";
		List replys =  hibernateDAO.getListByPageByObject(hql, map, start, row);
		List topics = new ArrayList();
		
		Iterator iterator = replys.iterator();
		while(iterator.hasNext()){
			Reply reply = (Reply) iterator.next();
			
			ReplyEnum replyEnum = reply.getReplyEnum();
			if(replyEnum == ReplyEnum.TopicReply){
				Integer topicid = reply.getReplyID();
				Topic topic = topicService.getTopicById(topicid);
				topics.add(topic);				
			}else{//非帖子回复此处不显示
				iterator.remove();
			}
			
		}
		
		map.clear();
		map.put("replys", replys);
		map.put("topics", topics);
		return map;
	}

	
	@Override
	public Reply queryTopicAnswer(Integer topicid) {
		if(topicid == null){
			return null;
		}
		
		Map map = new HashMap();
		map.put("topicid", topicid);
		String hql = "select tc.reply from TopicAnser tc WHERE tc.topic.id = :topicid";
		
		List list = hibernateDAO.getListByPage(hql, map, -1, 0);

		if(list != null && list.size() > 0){
			return (Reply) list.get(0);
		}
		
		return null;
	}

}
