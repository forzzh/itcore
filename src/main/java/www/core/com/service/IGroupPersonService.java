package www.core.com.service;

import java.util.List;

import www.core.com.pojo.Group;
import www.core.com.pojo.Groupperson;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;

public interface IGroupPersonService {
	
	public Message<User> getUserByGroupId(Integer groupid,Integer start, Integer row);
	
	public Message getGroupByUserId(Integer userId);
	public Groupperson add(Groupperson person);
	public Groupperson delete(Groupperson person);
	public Groupperson deleteIsNoLock(Groupperson person);
	public boolean isUserInGroup(Integer groupid, Integer userid) ;
	/**
	 * �ж��û��Ƿ��Ѽ���Ȧ�ӣ� ������ڷ���ʵ���� ������null
	 * 
	 * @param groupid
	 *            Ȧ��id
	 * @param userid
	 *            �û�id
	 * @return
	 */
	public Groupperson getUserInGroup(Integer groupid,Integer userid);
	
	/**
	 * ��ȡ�û����ڵ�Ȧ��
	 * @param userid
	 * @return
	 */
	public List<Group> getGroupByUser(Integer userid);

	public Message<Group> getGroupByUserid(Integer userid, int i, Integer row);

	public Message getCreateGroupByUserId(Integer userId);
}
