package www.core.com.service;

import java.util.List;
import java.util.Map;

import www.core.com.Enum.ReplyEnum;
import www.core.com.pojo.Message;
import www.core.com.pojo.Reply;

public interface IReplyService {

	/**
	 * ����id�����Ͳ�ѯ�ظ�
	 * ע:����д�answerid����𰸻ظ���������
	 * @param replyID
	 * @param pageNum
	 * @param count
	 * @param replyEnum
	 * @param answerid
	 * @return
	 */
	Message<Reply> queryReplyByReplyID(Integer replyID,Integer pageNum,Integer count,ReplyEnum replyEnum,Integer answerid);
	
	/**
	 * ��ѯ�û����лظ�
	 * @param userid
	 * @return
	 */
	public Map queryUserAllReply(Integer userid,int start,int row);

	/**
	 * ��ѯ���ӵĴ�
	 * @param id
	 * @return
	 */
	public Reply queryTopicAnswer(Integer topicid);
}
