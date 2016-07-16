package www.core.com.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import freemarker.template.utility.StringUtil;
import www.core.com.Enum.StatusEnum;
import www.core.com.Enum.UserEnum;
import www.core.com.dao.BaseDAO;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IUserPassUUIDService;
import www.core.com.service.IUserService;
import www.core.com.utils.Cn2Spell;
import www.core.com.utils.MD5;
import www.core.com.utils.*;

/**
 * @author kaja_yin
 *
 */

@Service("userService")
@Controller
@RequestMapping("user/")
@Transactional
@Configuration
// 加载资源文件
@PropertySource({ "classpath:/config/properties/sys.properties" })
public class UserServiceImpl extends AbstractService implements IUserService {

	@Value("${qiqiuUrl}")
	public String qiqiuUrl;

	@Resource(name = "hibernateDAO")
	BaseDAO<User> hibernateDAO;

	@Autowired
	IUserPassUUIDService userPassUUIDService;

	@Override
	public BaseDAO getHibernateDAO() {
		return hibernateDAO;
	}

	@Override
	public void setHibernateDAO(BaseDAO hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public Message login(User user) {
		// 查询user是否在数据库中
		Message<User> message = new Message();
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("email", user.getEmail());
		User result = hibernateDAO.findByColumn("from " + User.class.getSimpleName() + " where email=:email", parm);

		if (result == null || !user.getPassword().toUpperCase().equals(result.getPassword())) {
			message.setMsg("用户名密码错误");
			message.setStatus(false);
		} else if (result.getStatus() == UserEnum.noactive) {
			message.setMsg("用户未激活");
			message.setStatus(false);
			message.setBasic(result.getUser());
		} else if (result.getStatus() == UserEnum.delete) {

			message.setMsg("没有该用户");
			message.setStatus(false);
		} else {
			message.setMsg("登陆成功");
			message.setStatus(true);
			// result.setPassword(null);
			result.setImage(qiqiuUrl + result.getImage());
			message.setData(result);
		}

		// 返回查询结果
		return message;
	}

	public Message addUserInclude(User user, Message message) {
		User result = hibernateDAO.findByColumn(User.class, "user", user.getUser());
		if (result != null) {

			message.setMsg("昵称已存在");
			message.setStatus(false);
			return message;
		}
		result = hibernateDAO.findByColumn(User.class, "email", user.getEmail());
		if (result != null) {
			message.setMsg("邮箱已存在");
			message.setStatus(false);
			return message;
		}

		user.setChinaName(Cn2Spell.converterToSpell(user.getUser()));
		user.setPassword(MD5.getMD5(user.getPassword()));
		try {
			hibernateDAO.executeSearch(user);
			message.setStatus(true);
			return message;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		message.setStatus(false);
		message.setMsg("操作出错!");
		return message;
	}

	@Override
	@Transactional
	public Message regUser(User user) {
		// TODO Auto-generated method stub
		Message<User> message = new Message<>();

		user.setStatus(UserEnum.noactive);

		message = addUserInclude(user, message);
		if (message.isStatus()) {
			message.setMsg("用户注册成功");
		}

		return message;
	}

	@Override
	@Transactional
	public Message addUser(User user) {
		// TODO Auto-generated method stub
		Message<User> message = new Message<>();

		if (addUserInclude(user, message).isStatus()) {
			message.setStatus(true);
			message.setMsg("用户注册成功");
		} else {
			message.setMsg("注册失败");
			message.setStatus(false);
		}
		return message;
	}

	@Override
	public User userQuery(User user) {
		if (!(null == user.getId())) {
			return userQueryById(user.getId());
		}
		User result = hibernateDAO.findByColumn(User.class, "user", user.getUser());
		return result;

	}

	@Override
	public User userQueryById(Integer user) {
		User result = hibernateDAO.findByColumn(User.class, "id", (user));
		return result;
	}

	@Override
	public User userQueryByEmail(User user) {
		User result = hibernateDAO.findByColumn(User.class, "email", user.getEmail());
		return result;
	}

	@Override
	public User userQueryByName(User user) {
		User result = hibernateDAO.findByColumn(User.class, "user", user.getUser());
		return result;
	}

	/**
	 * //TODO 读取其它信息更改，目前只能更新密码，验证状态
	 */
	@Override
	public boolean updateUserInfo(User user) {
		// TODO 读取其它信息更改，目前只能改密码，验证状态
		User oldUser = userQuery(user);

		oldUser.setPassword(MD5.getMD5(user.getPassword()));
		oldUser.setStatus(user.getStatus());
		try {
			hibernateDAO.updateSearch(oldUser);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Message updateUserInfos(User user, Message message) {
		User oldUser = userQueryById(user.getId());
		oldUser.setEmail(user.getEmail());
		oldUser.setUser(user.getUser());
		oldUser.setChinaName(Cn2Spell.converterToSpell(user.getUser()));
		oldUser.setSex(user.getSex());
		oldUser.setStatus(user.getStatus());
		if (user.getPassword() != null) {
			oldUser.setPassword(user.getPassword());
		}
		try {
			hibernateDAO.updateSearch(oldUser);
			message.setStatus(true);
			message.setMsg("更改信息成功");
			return message;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		message.setStatus(false);
		message.setMsg("更改信息出错");
		return message;
	}

	@Override
	public Message modifyImage(String image, Integer x, Integer y, Integer width, Integer heigth, Integer userid,
			String staticUrl) throws IOException {
		ImageHeper.cutImage(staticUrl + image, staticUrl + image, x, y, width, heigth);
		Uploader upload = new Uploader();
		upload.upload(staticUrl + image, StringUtil.replace(image, "temp", "user"));
		User user = userQueryById(userid);
		String temp = user.getImage();
		user.setImage(StringUtil.replace(image, "temp", "user"));

		hibernateDAO.saveOrUpdate(user);
		Message message = new Message<User>();

		if (StringUtils.isNotEmpty(temp)) {
			upload.delete(image);
		}
		message.setStatus(true);
		message.setMsg("修改头像成功");
		return message;
	}

	@Override
	public User userQuery(Integer user) {

		User result = this.userQueryById(user);
		if (StringUtils.isNotEmpty(result.getImage())) {
			result.setImage(qiqiuUrl + result.getImage());
		}

		return result;
	}

	@Override
	public Message updateUserIndex() throws InterruptedException {
		Message message = new Message();

		List list = hibernateDAO.getAll(User.class);

		for (int i = 0; i < list.size(); i++) {
			User user = (User) list.get(i);
			hibernateDAO.updateSearch(user);
		}
		message.setStatus(true);
		message.setMsg("操作成功");
		return message;
	}
}
