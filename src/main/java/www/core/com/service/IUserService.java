package www.core.com.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import www.core.com.pojo.Message;
import www.core.com.pojo.User;

public interface IUserService {

	/**
	 * 查询用户是否存在
	 * 如果存在 
	 * msg.setStatus = true
	 * msg.setData = result
	 * @param user
	 * @return
	 */
	public User userQuery(User user);
	public User userQueryById(Integer user);
	public User userQueryByEmail(User user);
	public User userQueryByName(User user);
	public Message<User> login(User user);
	
	public Message<User> regUser(User user) throws Exception;
	
	public boolean updateUserInfo(User user);
	public Message<User> addUser(User user);
	public Message updateUserInfos(User user,Message message); 
	
	public Message modifyImage(String image,Integer x,Integer y ,Integer width ,Integer heigth ,Integer userid,String staticUrl) throws IOException;
	public User userQuery(Integer user);
	
	/**
	 * 更新用户查询索引
	 * @return
	 */
	public Message updateUserIndex() throws InterruptedException;
}
