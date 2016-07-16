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

		// �ȿ���û���Ѿ����ڵ��û���ϵ��û�оʹ����µĹ�ϵ
		Message<UserRelationship> getUserRelationshipMessage = this.getUserRelationship(passiveUserId, positiveUserId);


		// ���Ѵ��ڵĹ�ϵ���޸ľɵĹ�ϵ
		if (getUserRelationshipMessage.isStatus()) {
			UserRelationship oldRe = getUserRelationshipMessage.getData();
			// ����ǹ�ע״̬��ȡ����ע
			if (UserRelationshipEnum.follow.equals(oldRe.getRelationshipType())) {
				oldRe.setRelationshipType(UserRelationshipEnum.defollow);
			} else if (UserRelationshipEnum.defollow.equals(oldRe.getRelationshipType())) {
				oldRe.setRelationshipType(UserRelationshipEnum.follow);
			}
			// �������������
			// �����ݿ�������
			Message<UserRelationship> message = new Message<>();

			this.saveOrUpdate(oldRe);
			message.setMsg("�ı��ע״̬�ɹ�");
			message.setStatus(true);
			return message;

		}
		// ������ݿ��в����ڹ�ϵ
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
		// Ĭ���ǹ�ע
		newUserRelationship.setRelationshipType(UserRelationshipEnum.follow);

		// �����ݿ�������
		this.saveOrUpdate(newUserRelationship);

		Message<UserRelationship> message = new Message<>();
		message.setMsg("�ı��ע״̬�ɹ�");
		message.setStatus(true);
		return message;
	}

	/**
	 * ��ȡ�û��Ĺ�ϵ
	 * 
	 * @param passiveUserId
	 *            �类��ע��
	 * @param positiveUserId
	 *            ���˿
	 * @return
	 */
	@Override
	public Message<UserRelationship> getUserRelationship(Integer passiveUserId, Integer positiveUserId) {
		Message<UserRelationship> message = new Message<>();
		
		String hql = "from UserRelationship where passiveUser.id = :passiveUserId and  positiveUser.id =:positiveUserId";
		Map<String, Object> hqlMap = new HashMap<>();

		hqlMap.put("passiveUserId", passiveUserId);
		hqlMap.put("positiveUserId", positiveUserId);

		// �����ݿ���ȡ����
		UserRelationship relationship = this.findByColumn(hql, hqlMap);

		if (relationship == null) {
			message.setMsg("û�иù�ϵ����");
			message.setStatus(false);
			return message;
		}
		message.setMsg("��ѯ��ϵ�ɹ�");
		message.setStatus(true);
		message.setData(relationship);
		return message;
	}

	@Override
	public Message<UserRelationship> isFollowTogether(Integer passiveUserId, Integer positiveUserId) {

		if (passiveUserId == positiveUserId) {
			Message<UserRelationship> message = new Message<>();
			message.setStatus(true);
			message.setMsg("û�������Լ�");
			return message;
		}
		
		Message<UserRelationship> message1 = this.getUserRelationship(passiveUserId, positiveUserId);
		Message<UserRelationship> message2 = this.getUserRelationship(positiveUserId, passiveUserId);
		Message<UserRelationship> resultMsg = new Message<>();

		// ������¼�����������ע
		if (message1.isStatus() && message2.isStatus()) {
			Enum enum1 = message1.getData().getRelationshipType();
			Enum enum2 = message2.getData().getRelationshipType();
			if (UserRelationshipEnum.follow.equals(enum1) && UserRelationshipEnum.follow.equals(enum2)) {
				resultMsg.setMsg("�����ע");
				resultMsg.setStatus(true);
				return resultMsg;
			}
		}

		resultMsg.setMsg("�����ǻ����ע");
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
		resultMsg.setMsg("��ѯ�ɹ�");

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
		resultMsg.setMsg("��ѯ�ɹ�");

		return resultMsg;
	}

	@Override
	public Message addFriend(Integer passiveUserId, User user) {
		Message<Boolean> result = new Message<>();
		UserRelationship relation = getUserRelationship(passiveUserId, user.getId()).getData();
		if (relation != null) {
			hibernateDAO.delete(relation);
			result.setStatus(true);
			result.setMsg("ȡ����ע�ɹ���");
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
			result.setMsg("��Ӻ��ѳɹ���");
		}
		return result;
	}
}
