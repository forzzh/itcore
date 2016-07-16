package www.core.com.service;

import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.pojo.UserRelationship;

public interface IUserRelationshipService {
	/**
	 * 
	 * @param passiveUserId
	 *            被关注用户
	 * @param positiveUserId
	 *            粉丝用户
	 * @return
	 */
	public Message<UserRelationship> switchfollowStatus(Integer passiveUserId, Integer positiveUserId);

	/**
	 * 获取用户的关系
	 * 
	 * @param passiveUserId
	 *            如被关注者
	 * @param positiveUserId
	 *            如粉丝
	 * @return
	 */
	public Message<UserRelationship> getUserRelationship(Integer passiveUserId, Integer positiveUserId);

	/**
	 * 判断两个用户是否互相关注
	 * 
	 * @param passiveUserId
	 * @param positiveUserId
	 * @return
	 */
	public Message<UserRelationship> isFollowTogether(Integer passiveUserId, Integer positiveUserId);

	/**
	 * 得到粉丝列表
	 * 
	 * @param passiveUserId
	 * @return
	 */
	public Message<UserRelationship> getFansList(Integer passiveUserId, Integer start, Integer row);

	/**
	 * 得到关注列表
	 * @param positiveUserId
	 * @param start
	 * @param row
	 * @return
	 */
	public Message<User> getFollowerList(Integer positiveUserId, Integer start, Integer row);

	public Message addFriend(Integer passiveUserId, User user);
	
}
