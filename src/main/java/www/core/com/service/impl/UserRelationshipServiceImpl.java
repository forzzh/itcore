package www.core.com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.Enum.Enum;
import www.core.com.Enum.UserRelationshipEnum;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.pojo.UserRelationship;
import www.core.com.service.IUserRelationshipService;
import www.core.com.service.IUserService;

@Service("userRelationshipService")
@Transactional
public class UserRelationshipServiceImpl extends AbstractService<UserRelationship>implements IUserRelationshipService {

	@Autowired
	IUserService userService;

	@Override
	public Message<UserRelationship> switchfollowStatus(Integer passiveUserId, Integer positiveUserId) {

		// 先看有没有已经存在的用户关系，没有就创建新的关系
		Message<UserRelationship> getUserRelationshipMessage = this.getUserRelationship(passiveUserId, positiveUserId);


		// 有已存在的关系就修改旧的关系
		if (getUserRelationshipMessage.isStatus()) {
			UserRelationship oldRe = getUserRelationshipMessage.getData();
			// 如果是关注状态就取消关注
			if (UserRelationshipEnum.follow.equals(oldRe.getRelationshipType())) {
				oldRe.setRelationshipType(UserRelationshipEnum.defollow);
			} else if (UserRelationshipEnum.defollow.equals(oldRe.getRelationshipType())) {
				oldRe.setRelationshipType(UserRelationshipEnum.follow);
			}
			// 其它情况不管了
			// 向数据库存放数据
			Message<UserRelationship> message = new Message<>();

			this.saveOrUpdate(oldRe);
			message.setMsg("改变关注状态成功");
			message.setStatus(true);
			return message;

		}
		// 如果数据库中不存在关系
		UserRelationship newUserRelationship = new UserRelationship();
		User passiveUser = userService.userQueryById(passiveUserId);
		User positiveUser = userService.userQueryById(positiveUserId);

		if (passiveUser == null) {
			Message<UserRelationship> message = new Message<>();
			message.setMsg("null passiveUser");
			message.setStatus(false);
			return message;
		}

		if (positiveUser == null) {
			Message<UserRelationship> message = new Message<>();
			message.setMsg("null positiveUser");
			message.setStatus(false);
			return message;
		}

		newUserRelationship.setPassiveUserId(passiveUser);
		newUserRelationship.setPositiveUserId(positiveUser);
		// 默认是关注
		newUserRelationship.setRelationshipType(UserRelationshipEnum.follow);

		// 向数据库存放数据
		this.saveOrUpdate(newUserRelationship);

		Message<UserRelationship> message = new Message<>();
		message.setMsg("改变关注状态成功");
		message.setStatus(true);
		return message;
	}

	/**
	 * 获取用户的关系
	 * 
	 * @param passiveUserId
	 *            如被关注者
	 * @param positiveUserId
	 *            如粉丝
	 * @return
	 */
	@Override
	public Message<UserRelationship> getUserRelationship(Integer passiveUserId, Integer positiveUserId) {
		Message<UserRelationship> message = new Message<>();
		
		String hql = "from UserRelationship where passiveUser.id = :passiveUserId and  positiveUser.id =:positiveUserId";
		Map<String, Object> hqlMap = new HashMap<>();

		hqlMap.put("passiveUserId", passiveUserId);
		hqlMap.put("positiveUserId", positiveUserId);

		// 从数据库里取数据
		UserRelationship relationship = this.findByColumn(hql, hqlMap);

		if (relationship == null) {
			message.setMsg("没有该关系存在");
			message.setStatus(false);
			return message;
		}
		message.setMsg("查询关系成功");
		message.setStatus(true);
		message.setData(relationship);
		return message;
	}

	@Override
	public Message<UserRelationship> isFollowTogether(Integer passiveUserId, Integer positiveUserId) {

		if (passiveUserId == positiveUserId) {
			Message<UserRelationship> message = new Message<>();
			message.setStatus(true);
			message.setMsg("没错是你自己");
			return message;
		}
		
		Message<UserRelationship> message1 = this.getUserRelationship(passiveUserId, positiveUserId);
		Message<UserRelationship> message2 = this.getUserRelationship(positiveUserId, passiveUserId);
		Message<UserRelationship> resultMsg = new Message<>();

		// 两个记录都存在则互相关注
		if (message1.isStatus() && message2.isStatus()) {
			Enum enum1 = message1.getData().getRelationshipType();
			Enum enum2 = message2.getData().getRelationshipType();
			if (UserRelationshipEnum.follow.equals(enum1) && UserRelationshipEnum.follow.equals(enum2)) {
				resultMsg.setMsg("互相关注");
				resultMsg.setStatus(true);
				return resultMsg;
			}
		}

		resultMsg.setMsg("并不是互相关注");
		resultMsg.setStatus(false);
		return resultMsg;
	}

	@Override
	public Message<UserRelationship> getFansList(Integer passiveUserId, Integer start, Integer row) {
		Message<UserRelationship> resultMsg = new Message<>();

		Map<String, Object> args = new HashMap<>();
		args.put("passiveUserId", passiveUserId);

		List Fans = hibernateDAO.getListByPageByObject(
				"select ur.positiveUser from UserRelationship ur where ur.passiveUser.id = :passiveUserId", args, start,
				row);

		resultMsg.setList(Fans);
		resultMsg.setStatus(true);
		resultMsg.setMsg("查询成功");

		return resultMsg;
	}

	@Override
	public Message<User> getFollowerList(Integer positiveUserId, Integer start, Integer row) {
		Message<User> resultMsg = new Message<>();

		Map<String, Object> args = new HashMap<>();
		args.put("positiveUserId", positiveUserId);

		List Followers = hibernateDAO.getListByPageByObject(
				"select ur.passiveUser from UserRelationship ur where ur.positiveUser.id = :positiveUserId", args,
				start, row);

		resultMsg.setList(Followers);
		resultMsg.setStatus(true);
		resultMsg.setMsg("查询成功");

		return resultMsg;
	}

	@Override
	public Message addFriend(Integer passiveUserId, User user) {
		Message<Boolean> result = new Message<>();
		UserRelationship relation = getUserRelationship(passiveUserId, user.getId()).getData();
		if (relation != null) {
			hibernateDAO.delete(relation);
			result.setStatus(true);
			result.setMsg("取消关注成功！");
		} else {
			User passiveUser = new User();
			passiveUser.setId(passiveUserId);
			relation = new UserRelationship();
			relation.setPassiveUserId(passiveUser);
			relation.setPositiveUserId(user);
			relation.setRelationshipType(UserRelationshipEnum.follow);
			hibernateDAO.save(relation);
			result.setStatus(true);
			result.setData(true);
			result.setMsg("添加好友成功！");
		}
		return result;
	}
}
