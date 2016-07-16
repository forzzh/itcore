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
	 * �û��˳�Ȧ�ӵ���Ϣ����
	 * ע:�˷���ֻ��������Ϣ�����������û��˳�Ȧ�Ӳ���
	 * @param user �˳�Ȧ���û�
	 * @param group Ȧ��
	 */
	public void exitGroupMessage(User user ,Group group);

	public Message<MessageSys> queryUserMsgList(Integer pageNum, Integer count, Integer userId,StatusEnum status);


	public Message<MessageSys> list(Integer userid, Integer start,
			Integer pageNum);

	public Message unreadMessageCount(Integer id);

	/**
	 * �����û�������ϢΪ�Ѷ�
	 * @param user
	 * @return
	 */
	public Message setAllRead(User user);

	/**
	 * ɾ���û�������Ϣ
	 * @param user
	 * @return
	 */
	public Message delteAllMsg(User user);
}
