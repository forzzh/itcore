package www.core.com.service;

import www.core.com.pojo.Group;
import www.core.com.pojo.Message;
import www.core.com.pojo.MessageSys;
import www.core.com.pojo.User;
import www.core.com.Enum.StatusEnum;

public interface IMessageSysService {

	public Message<MessageSys> addMessageSys(MessageSys messageSys);
	
	public Message<MessageSys> deleteMessageSys(Integer msgID);

	public Message<MessageSys> updateMsgRead(Integer msgID);

	public Message<MessageSys> getMsgList(Integer page, Integer limit, StatusEnum status);

	public Message<MessageSys> agreeMsg(User user,Integer msgID,StatusEnum status);
	
	/**
	 * 用户退出圈子的消息发送
	 * 注:此方法只负责发送消息，并不处理用户退出圈子操作
	 * @param user 退出圈子用户
	 * @param group 圈子
	 */
	public void exitGroupMessage(User user ,Group group);

	public Message<MessageSys> queryUserMsgList(Integer pageNum, Integer count, Integer userId,StatusEnum status);


	public Message<MessageSys> list(Integer userid, Integer start,
			Integer pageNum);

	public Message unreadMessageCount(Integer id);

	/**
	 * 设置用户所有消息为已读
	 * @param user
	 * @return
	 */
	public Message setAllRead(User user);

	/**
	 * 删除用户所有消息
	 * @param user
	 * @return
	 */
	public Message delteAllMsg(User user);
}
