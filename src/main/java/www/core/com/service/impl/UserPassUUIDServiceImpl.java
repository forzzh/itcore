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
	 * 得到find-password的email正文
	 */
	@Override
	public Message<IUserPassUUIDService> getResetPasswordUUID(User user) {
		return this.get(user, VerifyCodeTypeEnum.findpassword);
	}

	/**
	 * 验证find-password的回执的请求
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
	 * 得到activeUser的email正文
	 */
	@Override
	public Message<IUserPassUUIDService> getActiveUserEmail(User user) {
		return this.get(user, VerifyCodeTypeEnum.activeUser);
	}

	/**
	 * 处理activeUser的请求
	 */
	@Override
	public Message<IUserPassUUIDService> hadleActiveUser(String UUID) {

		VerifyCode verifyCode = findByUUID(UUID);
		Message<IUserPassUUIDService> message;

		if (null == verifyCode) {
			message = new Message<>();
			message.setMsg("激活验证错误");
			message.setStatus(false);
			return message;
		}

		// used
		if (VerifyCodeEnum.use.equals(verifyCode.getStatus())) {
			message = new Message<>();
			message.setMsg("验证链接已被使用");
			message.setStatus(false);
			return message;
		}

		if (isOutOfTime(verifyCode)) {
			message = new Message<>();
			message.setMsg("验证链接超时");
			message.setStatus(false);
			return message;
		}

		// not activeUser request
		if (!VerifyCodeTypeEnum.activeUser.equals(verifyCode.getType())) {
			message = new Message<>();
			message.setMsg("错误的请求");
			message.setStatus(false);
			return message;
		}

		User user = verifyCode.getUser();
		user.setStatus(UserEnum.active);

		hibernateDAO.saveOrUpdate(user);

		verifyCode.setStatus(VerifyCodeEnum.use);
		
		message = new Message<>();
		message.setMsg("验证成功");
		message.setStatus(true);
		message.setBasic(verifyCode.getUser().getEmail());
		return message;
	}

	/**
	 * 得到对应的email正文
	 * 
	 * @param user
	 * @param verifyCodeType
	 * @return
	 */
	Message<IUserPassUUIDService> get(User user, VerifyCodeTypeEnum verifyCodeType) {
		Message<IUserPassUUIDService> message;
		User result = userService.userQueryByEmail(user);
		// 没有该用户，失败
		if (result == null) {
			message = new Message<>();
			message.setMsg("没有该用户,");
			message.setStatus(false);
			return message;
		}
		

		

		// 生成一个UUID
		BaseController<VerifyCode> baseController = new BaseController<>();
		String uuid = baseController.createCode();

		// UUID-user-id对应关系入库
		VerifyCode userPassUUID = new VerifyCode();

		userPassUUID.setUser(result);
		userPassUUID.setCode(uuid);

		message = new Message<>();
		message.setMsg("错误的请求");
		message.setStatus(false);
		// 如果是处理找回密码的业务
		if (VerifyCodeTypeEnum.findpassword.equals(verifyCodeType)) {
			
			if (result.getStatus() != UserEnum.active) {
				message = new Message<>();
				message.setMsg("没有该用户,请发送邮件去question@corer.me");
				message.setStatus(false);
				return message;
			}
			
			// 需要检查这个用户是否频繁发验证码 如果频率较高 请不要再几分钟内重复发邮件
			if (isAlreadySend(result,verifyCodeType)) {
				message = new Message<>();
				message.setMsg("请勿在一分钟内连续提交");
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
				message.setMsg("用户未激活");
				message.setStatus(false);
			}

		}
		// 如果是激活用户的业务
		if (VerifyCodeTypeEnum.activeUser.equals(verifyCodeType)) {

			if (result.getStatus().equals(UserEnum.noactive)) {

				userPassUUID.setStatus(VerifyCodeEnum.nouse);
				userPassUUID.setType(verifyCodeType);
				hibernateDAO.saveOrUpdate(userPassUUID);

				String UUID = userPassUUID.getCode();
				message.setMsg(UUID);
				message.setStatus(true);

			} else {
				message.setMsg("用户已经激活");
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
		// 超过10分钟
		if ((nowTime.getTime() - verifyCode.getCreateDate().getTime()) > 600000) {
			verifyCode.setStatus(VerifyCodeEnum.use);
			return true;
		}
		return false;

	}

	/**
	 * 验证码是否有效
	 */
	@Override
	public Message<IUserPassUUIDService> isEnable(VerifyCode verifyCode) {
		Message<IUserPassUUIDService> message = new Message<>();
		
		if (this.isOutOfTime(verifyCode)) {
			message.setMsg("验证码无效");
			message.setStatus(false);
			return message;
		}

		if (this.findByUUID(verifyCode.getCode()) == null) {
			message.setMsg("验证码无效");
			message.setStatus(false);
			return message;
		}
		message.setMsg("验证码有效");
		message.setStatus(true);
		return message;
	}

	@Override
	public Message<IUserPassUUIDService> isEnable(String UUID) {
		Message<IUserPassUUIDService> message = new Message<>();
		VerifyCode verifyCode = this.findByUUID(UUID);
		
		if (verifyCode == null) {
			message.setMsg("验证码无效");
			message.setStatus(false);
			return message;
		}
		if (this.isOutOfTime(verifyCode)) {
			message.setMsg("验证码无效");
			message.setStatus(false);
			return message;
		}
		message.setMsg("验证码有效");
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
		// 大于1分钟
		if ((nowTime.getTime() - verifyCode.getCreateDate().getTime()) > 60000) {
			return false;
		}

		return true;
	}
}
