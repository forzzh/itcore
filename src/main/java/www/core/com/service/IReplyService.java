package www.core.com.service;

import java.util.List;
import java.util.Map;

import www.core.com.Enum.ReplyEnum;
import www.core.com.pojo.Message;
import www.core.com.pojo.Reply;

public interface IReplyService {

	/**
	 * 根据id和类型查询回复
	 * 注:如果有答案answerid，则答案回复将被被排
	 * @param replyID
	 * @param pageNum
	 * @param count
	 * @param replyEnum
	 * @param answerid
	 * @return
	 */
	Message<Reply> queryReplyByReplyID(Integer replyID,Integer pageNum,Integer count,ReplyEnum replyEnum,Integer answerid);
	
	/**
	 * 查询用户所有回复
	 * @param userid
	 * @return
	 */
	public Map queryUserAllReply(Integer userid,int start,int row);

	/**
	 * 查询帖子的答案
	 * @param id
	 * @return
	 */
	public Reply queryTopicAnswer(Integer topicid);
}
