package www.core.com.service;


import java.io.IOException;
import java.util.List;

import www.core.com.Enum.StatusEnum;
import www.core.com.pojo.Group;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;

/**
 * @author BlindingDark
 *
 */

public interface IGroupService {	
		
	public Message<Group> exitGroup(Integer groupid, Integer userid);
	/**
	 * ����group
	 * �ɹ� message.status = true;
	 * ʧ�� message.status = false;
	 * @param group
	 * @return
	 * extlabel ����չ��ǩ
	 * @throws IOException 
	 */
	public Message<Group> addNewGroup(Group group,String admin,String staticPath,String... extLabel) throws IOException;
	
	//ɾ��group
	public Message<Group> deleteGroup(Group group);
	
	
	
	
	
	/**
	 * ��group����,message.status = true;
	 * ��group������,message.status = false;
	 * @param group
	 * @return Message
	 */
	public Group groupExistence(Group group);
	
	/**
	 * ����id��ѯgroup
	 * @param groupid
	 * @return
	 */
	public Group getGroupById(Integer groupid);
	
	
	/**
	 * ����Ȧ������
	 * ���³ɹ� message.status = true;
	 * ����ʧ�� message.status = false;
	 * @param group Ҫ���µ�����
	 * @return Message
	 */
	public Message<Group> updateGroupInfo(Group group);
	
	
	
	/**
	 * ����Ȧ��
	 * group.foucsPersonNum++
	 * Group-person��һ��ϵ�������µ���ϵ
	 * ����ɹ�����message.status = true;
	 * ����ʧ������message.status = false;
	 * 
	 * @param group Ҫ�����Ȧ��
	 * @param user Ҫ������û�
	 * @return Message
	 */
	public Message<Group> joinGroup(Group group,User user);
	
	//�뿪Ȧ��
	public Message<Group> quitGroup(Group group,User user);
	
	/**
	 * �õ�����group�б�
	 * ������group.score����
	 * @param numofTop Ҫ�õ�������
	 * @return Message.list Ϊ���ŵ�group�б�
	 */
	public Message<Group> getHotGroup(int numofTop);
	
	/**
	 * �������� Group score
	 * ��ʽ�ݶ�score = Group.foucsPersonNum * 5 + Group.topics
	 * ����ɹ����֮�� ����message.status = true;
	 * �÷����ڣ�
	 * �û�����joinGroup���û��뿪quitGroup���û���Ȧ���﷢�����û�ɾ��
	 * �����д���
	 * @param group Ҫ���µ�Ȧ��
	 * @return Message
	 */
	public Message<Group> updateHotGroup(Group group);
	
	/**
	 * Ȧ����ϸ ����groupid
	 * @param group
	 * @return
	 */
	public Message<Group> detailGroup(Group group);
	/**
	 * ��Ȧ�ӽӿ�
	 * @param groupid ��Ȧ��id
	 * @param userid �����˵��û�id 
	 * @return
	 */
	public Message<Group> addGroup(Integer groupId,Integer userid);	
	/**
	 * 
	 * @param groupId ����˵�Ȧ��id
	 * @param userid ������
	 * @param outUserId ������û�id
	 * @return
	 */
	public Message<Group> outGroup(Integer groupId,Integer userid,Integer outUserId);	
	/**
	 * ��������
	 * @param id
	 * @return
	 */
	public Group groupExistenceLock(Integer id) ;
	
	/**
	 * ��˴���Ȧ������
	 * @param user ��½�û�
	 * @param groupId Ȧ��id
	 * @param statusEnum ����
	 * @return
	 */
	public Message reviewGroup(User user,Integer groupId, StatusEnum statusEnum);
	
	/**
	 * �޸��û�����
	 * @param document
	 * @param id
	 * @param userId
	 * @return
	 */
	public Message updateDocument(String document,Integer id,Integer userId);
	public Message<Label> getCreate(Integer id, Integer pageNum,
			Integer count);
	public Message<Label> getfocus(Integer id, Integer pageNum,
			Integer count);
	
	
	/**
	 * �õ��Ƽ�Ȧ��
	 * @param user
	 * @return
	 */
	public Message<Group> getRecommendGroup(User user, Boolean isLogin);
	/**
	 *��ȡȦ�ӷ�ҳ
	 *@param pageNow
	 *@param count
	 *@return
	 */
	public Message<Group> getlistGroup( Integer pageNow,
			Integer count);
	public Message<Group> searchByname(String name);
}
