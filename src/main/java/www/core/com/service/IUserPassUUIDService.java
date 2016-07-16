package www.core.com.service;

import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.pojo.VerifyCode;

public interface IUserPassUUIDService {

	public Message<IUserPassUUIDService> getActiveUserEmail(User user);

	public Message<IUserPassUUIDService> hadleActiveUser(String UUID);

	public Message<IUserPassUUIDService> getResetPasswordUUID(User user);

	public boolean hadleResetPassword(String PassUUID,String newPassword);
	
	public boolean isOutOfTime(VerifyCode verifyCode);
	
	public Message<IUserPassUUIDService> isEnable(VerifyCode verifyCode);
	public Message<IUserPassUUIDService> isEnable(String UUID);
	

}
