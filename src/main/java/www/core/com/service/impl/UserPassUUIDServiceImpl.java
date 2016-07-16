package www.core.com.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.core.com.Enum.UserEnum;
import www.core.com.Enum.VerifyCodeEnum;
import www.core.com.Enum.VerifyCodeTypeEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.dao.BaseDAO;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.pojo.VerifyCode;
import www.core.com.service.IUserPassUUIDService;
import www.core.com.service.IUserService;

@Service("userPassUUIDService")
@Transactional
public class UserPassUUIDServiceImpl implements IUserPassUUIDService {

	@Resource(name = "hibernateDAO")
	BaseDAO<VerifyCode> hibernateDAO;

	@Autowired
	IUserService userService;

	/**
	 * �õ�find-password��email����
	 */
	@Override
	public Message<IUserPassUUIDService> getResetPasswordUUID(User user) {
		return this.get(user, VerifyCodeTypeEnum.findpassword);
	}

	/**
	 * ��֤find-password�Ļ�ִ������
	 */
	@Override
	public boolean hadleResetPassword(String PassUUID, String newPassword) {

		VerifyCode verifyCode = findByUUID(PassUUID);
		if (null == verifyCode) {
			return false;
		}
		if (isOutOfTime(verifyCode)) {
			return false;
		}
		// used
		if (VerifyCodeEnum.use.equals(verifyCode.getStatus())) {
			return false;
		}
		// not find-password request
		if (!VerifyCodeTypeEnum.findpassword.equals(verifyCode.getType())) {
			return false;
		}

		User user = verifyCode.getUser();
		user.setPassword(newPassword);

		userService.updateUserInfo(user);
		verifyCode.setStatus(VerifyCodeEnum.use);

		return true;
	}

	/**
	 * �õ�activeUser��email����
	 */
	@Override
	public Message<IUserPassUUIDService> getActiveUserEmail(User user) {
		return this.get(user, VerifyCodeTypeEnum.activeUser);
	}

	/**
	 * ����activeUser������
	 */
	@Override
	public Message<IUserPassUUIDService> hadleActiveUser(String UUID) {

		VerifyCode verifyCode = findByUUID(UUID);
		Message<IUserPassUUIDService> message;

		if (null == verifyCode) {
			message = new Message<>();
			message.setMsg("������֤����");
			message.setStatus(false);
			return message;
		}

		// used
		if (VerifyCodeEnum.use.equals(verifyCode.getStatus())) {
			message = new Message<>();
			message.setMsg("��֤�����ѱ�ʹ��");
			message.setStatus(false);
			return message;
		}

		if (isOutOfTime(verifyCode)) {
			message = new Message<>();
			message.setMsg("��֤���ӳ�ʱ");
			message.setStatus(false);
			return message;
		}

		// not activeUser request
		if (!VerifyCodeTypeEnum.activeUser.equals(verifyCode.getType())) {
			message = new Message<>();
			message.setMsg("���������");
			message.setStatus(false);
			return message;
		}

		User user = verifyCode.getUser();
		user.setStatus(UserEnum.active);

		hibernateDAO.saveOrUpdate(user);

		verifyCode.setStatus(VerifyCodeEnum.use);
		
		message = new Message<>();
		message.setMsg("��֤�ɹ�");
		message.setStatus(true);
		message.setBasic(verifyCode.getUser().getEmail());
		return message;
	}

	/**
	 * �õ���Ӧ��email����
	 * 
	 * @param user
	 * @param verifyCodeType
	 * @return
	 */
	Message<IUserPassUUIDService> get(User user, VerifyCodeTypeEnum verifyCodeType) {
		Message<IUserPassUUIDService> message;
		User result = userService.userQueryByEmail(user);
		// û�и��û���ʧ��
		if (result == null) {
			message = new Message<>();
			message.setMsg("û�и��û�,");
			message.setStatus(false);
			return message;
		}
		

		

		// ����һ��UUID
		BaseController<VerifyCode> baseController = new BaseController<>();
		String uuid = baseController.createCode();

		// UUID-user-id��Ӧ��ϵ���
		VerifyCode userPassUUID = new VerifyCode();

		userPassUUID.setUser(result);
		userPassUUID.setCode(uuid);

		message = new Message<>();
		message.setMsg("���������");
		message.setStatus(false);
		// ����Ǵ����һ������ҵ��
		if (VerifyCodeTypeEnum.findpassword.equals(verifyCodeType)) {
			
			if (result.getStatus() != UserEnum.active) {
				message = new Message<>();
				message.setMsg("û�и��û�,�뷢���ʼ�ȥquestion@corer.me");
				message.setStatus(false);
				return message;
			}
			
			// ��Ҫ�������û��Ƿ�Ƶ������֤�� ���Ƶ�ʽϸ� �벻Ҫ�ټ��������ظ����ʼ�
			if (isAlreadySend(result,verifyCodeType)) {
				message = new Message<>();
				message.setMsg("������һ�����������ύ");
				message.setStatus(false);
				return message;
			}
			if (result.getStatus().equals(UserEnum.active)) {

				userPassUUID.setStatus(VerifyCodeEnum.nouse);
				userPassUUID.setType(verifyCodeType);
				hibernateDAO.saveOrUpdate(userPassUUID);
				message.setUser(result);
				message.setMsg(userPassUUID.getCode());
				message.setStatus(true);

			} else {
				message.setMsg("�û�δ����");
				message.setStatus(false);
			}

		}
		// ����Ǽ����û���ҵ��
		if (VerifyCodeTypeEnum.activeUser.equals(verifyCodeType)) {

			if (result.getStatus().equals(UserEnum.noactive)) {

				userPassUUID.setStatus(VerifyCodeEnum.nouse);
				userPassUUID.setType(verifyCodeType);
				hibernateDAO.saveOrUpdate(userPassUUID);

				String UUID = userPassUUID.getCode();
				message.setMsg(UUID);
				message.setStatus(true);

			} else {
				message.setMsg("�û��Ѿ�����");
				message.setStatus(false);
			}

		}

		return message;
	}

	public VerifyCode findByUUID(String PassUUID) {

		VerifyCode result = hibernateDAO.findByColumn(VerifyCode.class, "code", PassUUID);
		return result;

	}

	@Override
	public boolean isOutOfTime(VerifyCode verifyCode) {

		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		// ����10����
		if ((nowTime.getTime() - verifyCode.getCreateDate().getTime()) > 600000) {
			verifyCode.setStatus(VerifyCodeEnum.use);
			return true;
		}
		return false;

	}

	/**
	 * ��֤���Ƿ���Ч
	 */
	@Override
	public Message<IUserPassUUIDService> isEnable(VerifyCode verifyCode) {
		Message<IUserPassUUIDService> message = new Message<>();
		
		if (this.isOutOfTime(verifyCode)) {
			message.setMsg("��֤����Ч");
			message.setStatus(false);
			return message;
		}

		if (this.findByUUID(verifyCode.getCode()) == null) {
			message.setMsg("��֤����Ч");
			message.setStatus(false);
			return message;
		}
		message.setMsg("��֤����Ч");
		message.setStatus(true);
		return message;
	}

	@Override
	public Message<IUserPassUUIDService> isEnable(String UUID) {
		Message<IUserPassUUIDService> message = new Message<>();
		VerifyCode verifyCode = this.findByUUID(UUID);
		
		if (verifyCode == null) {
			message.setMsg("��֤����Ч");
			message.setStatus(false);
			return message;
		}
		if (this.isOutOfTime(verifyCode)) {
			message.setMsg("��֤����Ч");
			message.setStatus(false);
			return message;
		}
		message.setMsg("��֤����Ч");
		message.setStatus(true);
		return message;

	}

	public boolean isAlreadySend(User user,VerifyCodeTypeEnum verifyCodeType) {

		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("userid", user.getId());
		String hql = "from VerifyCode where user.id = :userid and  type ='"+verifyCodeType+"' order by createDate desc";
		VerifyCode verifyCode = hibernateDAO.findByColumn(hql, hqlMap);
		if (verifyCode == null) {
			return false;
		}
		
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		// ����1����
		if ((nowTime.getTime() - verifyCode.getCreateDate().getTime()) > 60000) {
			return false;
		}

		return true;
	}
}
