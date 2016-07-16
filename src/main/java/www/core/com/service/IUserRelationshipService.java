package www.core.com.service;

import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.pojo.UserRelationship;

public interface IUserRelationshipService {
	/**
	 * 
	 * @param passiveUserId
	 *            ����ע�û�
	 * @param positiveUserId
	 *            ��˿�û�
	 * @return
	 */
	public Message<UserRelationship> switchfollowStatus(Integer passiveUserId, Integer positiveUserId);

	/**
	 * ��ȡ�û��Ĺ�ϵ
	 * 
	 * @param passiveUserId
	 *            �类��ע��
	 * @param positiveUserId
	 *            ���˿
	 * @return
	 */
	public Message<UserRelationship> getUserRelationship(Integer passiveUserId, Integer positiveUserId);

	/**
	 * �ж������û��Ƿ����ע
	 * 
	 * @param passiveUserId
	 * @param positiveUserId
	 * @return
	 */
	public Message<UserRelationship> isFollowTogether(Integer passiveUserId, Integer positiveUserId);

	/**
	 * �õ���˿�б�
	 * 
	 * @param passiveUserId
	 * @return
	 */
	public Message<UserRelationship> getFansList(Integer passiveUserId, Integer start, Integer row);

	/**
	 * �õ���ע�б�
	 * @param positiveUserId
	 * @param start
	 * @param row
	 * @return
	 */
	public Message<User> getFollowerList(Integer positiveUserId, Integer start, Integer row);

	public Message addFriend(Integer passiveUserId, User user);
	
}
